package bitfinex.callback;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.function.Consumer;

/**
 * Callback to invoke on a new message from bitfinex
 */
public class TradeCallback implements Consumer<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeCallback.class);

    /**
     * Invoke action for message from bfx
     * @param message message with format:
     *      [ 5, 'te', '1234-BTCUSD', 1443659698, 236.42, 0.49064538 ]
     */
    @Override
    public void accept(String message) {
        if (StringUtils.isNotEmpty(message) && message.contains("te")) {
            JSONArray messageAsJson = new JSONArray(message);
            LOGGER.info("Got trade: [" + tradeAsString(messageAsJson) + "]");
        }
    }

    private String tradeAsString(JSONArray tradeData) {
        return tradeData.getDouble(4) + "," +
                tradeData.getDouble(5) + "," +
                new Date(tradeData.getLong(3) * 1000);
    }
}
