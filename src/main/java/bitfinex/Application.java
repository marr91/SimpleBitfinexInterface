package bitfinex;

import bitfinex.client.BitfinexRestClient;
import bitfinex.json.SymbolDetail;
import bitfinex.subscriber.BfxTradesSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Autowired
    private BfxTradesSubscriber bfxTradesSubscriber;

    @Autowired
    private BitfinexRestClient bfxRestClient;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    public void run(String... args) {
        List<SymbolDetail> symbolDetails = bfxRestClient.getSymbolDetails();
        printSymbolDetails(symbolDetails);

        bfxTradesSubscriber.subscribeOnTradePair("BTCUSD");
    }

    private void printSymbolDetails(List<SymbolDetail> symbolDetails) {
        StringBuilder sb = new StringBuilder();
        symbolDetails.forEach(sd -> sb.append(sd.toSimplifiedString()).append("\n"));
        LOGGER.info("Got bitfinex symbol details: \n" + sb.toString());
    }
}
