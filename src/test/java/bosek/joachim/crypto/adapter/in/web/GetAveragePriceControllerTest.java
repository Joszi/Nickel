package bosek.joachim.crypto.adapter.in.web;

import bosek.joachim.crypto.application.domain.model.AveragePrice;
import bosek.joachim.crypto.application.port.in.GetMovingAveragePriceQuery;
import bosek.joachim.crypto.application.port.in.GetMovingAveragePriceUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GetAveragePriceController.class)
class GetAveragePriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetMovingAveragePriceUseCase getMovingAveragePriceUseCase;

    @Test
    void testGetAveragePriceForSymbol() throws Exception {
        //given
        String symbol = "btcusdt";
        String value = "10.55";
        GetMovingAveragePriceQuery getMovingAveragePriceQuery = new GetMovingAveragePriceQuery(symbol);

        when(getMovingAveragePriceUseCase.getMovingAverage(getMovingAveragePriceQuery))
                .thenReturn(AveragePrice.of(new BigDecimal(value)));

        //when //then
        mockMvc.perform(get("/api/moving-average/{symbol}", symbol)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.average").value(value));

        verify(getMovingAveragePriceUseCase).getMovingAverage(getMovingAveragePriceQuery);
    }
}
