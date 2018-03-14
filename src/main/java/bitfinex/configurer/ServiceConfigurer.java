package bitfinex.configurer;


import bitfinex.callback.TradeCallback;
import bitfinex.client.BitfinexRestClient;
import bitfinex.client.BitfinexWsClient;
import bitfinex.subscriber.BfxTradesSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Beans configuration
 */
@Configuration
public class ServiceConfigurer {

    @Bean(destroyMethod = "close")
    public BitfinexWsClient bitfinexWsClient() {
        // TODO: should be extracted to config
        String wsUri = "wss://api.bitfinex.com/ws/1";

        return new BitfinexWsClient(wsUri);
    }

    @Bean
    public BfxTradesSubscriber bfxTradesSubscriber(BitfinexWsClient bfxClient, TradeCallback tradeCallback) {
        return new BfxTradesSubscriber(bfxClient, tradeCallback);
    }

    @Bean
    public TradeCallback tradeCallback() {
        return new TradeCallback();
    }

    @Bean
    public BitfinexRestClient bitfinexRestClient() {
        return new BitfinexRestClient(new RestTemplate());
    }
}
