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

    @Override
    public void accept(String message) {
        if (StringUtils.isNotEmpty(message) && message.contains("te")) {
            JSONArray messageAsJson = new JSONArray(message);
            JSONArray tradeData = (JSONArray) messageAsJson.get(2);
            LOGGER.info("Got trade: " + tradeAsString(tradeData));
        }
    }

    private String tradeAsString(JSONArray tradeData) {
        return tradeData.getDouble(3) + ", " +
                tradeData.getDouble(2) + ", " +
                new Date(tradeData.getLong(1));
    }
}
