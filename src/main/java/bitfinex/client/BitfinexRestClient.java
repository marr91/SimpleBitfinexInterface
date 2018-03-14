package bitfinex.client;

import bitfinex.json.SymbolDetail;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class BitfinexRestClient {

    private static final String SYMBOL_DETAILS_URL = "https://api.bitfinex.com/v1/symbols_details";

    private final RestTemplate restTemplate;

    public BitfinexRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get symbol details (
     * @return
     */
    public List<SymbolDetail> getSymbolDetails() {
        ResponseEntity<List<SymbolDetail>> responseEntity = restTemplate.exchange(SYMBOL_DETAILS_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SymbolDetail>>() {
                });

        return responseEntity.getBody();
    }
}
