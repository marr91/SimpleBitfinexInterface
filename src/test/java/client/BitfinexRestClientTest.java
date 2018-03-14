package client;

import bitfinex.client.BitfinexRestClient;
import bitfinex.json.SymbolDetail;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


// TODO: not finished, add more test cases
@RunWith(MockitoJUnitRunner.class)
public class BitfinexRestClientTest {

    private static final String SYMBOL_DETAILS_URL = "https://api.bitfinex.com/v1/symbols_details";

    private BitfinexRestClient bitfinexRestClient;

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private ResponseEntity responseEntityMock;

    @Before
    public void setup() {
        bitfinexRestClient = new BitfinexRestClient(restTemplateMock);
        when(restTemplateMock.exchange(SYMBOL_DETAILS_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<SymbolDetail>>() {
        })).thenReturn(responseEntityMock);
    }

    @Test
    public void shouldReturnListOfSymbolDetails() {
        // given
        List<SymbolDetail> mockedSymbolDetails = ImmutableList.of(new SymbolDetail());
        when(responseEntityMock.getBody()).thenReturn(mockedSymbolDetails);

        // when
        List<SymbolDetail> symbolDetails = bitfinexRestClient.getSymbolDetails();

        // then
        assertThat(symbolDetails).isEqualTo(mockedSymbolDetails);
    }
}
