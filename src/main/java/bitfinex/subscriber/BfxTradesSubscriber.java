package bitfinex.subscriber;

import bitfinex.callback.TradeCallback;
import bitfinex.client.BitfinexWsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Subscribes for real-time trades from bitfinex, using WS API
 */
public class BfxTradesSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(BfxTradesSubscriber.class);

    private BitfinexWsClient bfxWsClient;

    private TradeCallback tradeCallback;

    private static final String SUBSCRIBE_TRADES_MSG_PATTERN = "{\n" +
            "  \"event\": \"subscribe\",\n" +
            "  \"channel\": \"trades\",\n" +
            "  \"pair\": \"%s\"\n" +
            "}";

    public BfxTradesSubscriber(BitfinexWsClient bitfinexWsClient, TradeCallback tradeCallback) {
        this.bfxWsClient = bitfinexWsClient;
        this.tradeCallback = tradeCallback;
    }

    /**
     * Subscribe to bitfinex trades using WS API
     * @param pair trading pair, eg "BTCUSD"
     */
    public void subscribeOnTradePair(String pair) {
        if (!bfxWsClient.isConnected()) {
            bfxWsClient.connect();
        }
        bfxWsClient.addCallback(tradeCallback);
        bfxWsClient.sendMessage(String.format(SUBSCRIBE_TRADES_MSG_PATTERN, pair));

        LOGGER.info("Subscribed on bitfinex trades");
    }

    public void unsubscribe() {
        bfxWsClient.removeCallback(tradeCallback);
    }
}
