package bosek.joachim.crypto.application.domain.service;

import bosek.joachim.crypto.application.domain.model.AveragePrice;
import bosek.joachim.crypto.application.port.in.GetMovingAveragePriceQuery;
import bosek.joachim.crypto.application.port.out.MovingAveragePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetMovingAveragePriceServiceTest {

    private final MovingAveragePort movingAveragePort = Mockito.mock(MovingAveragePort.class);

    private final GetMovingAveragePriceService getMovingAveragePriceService = new GetMovingAveragePriceService(movingAveragePort);

    @Test
    void shouldReturnMovingAveragePrice() {
        //given
        String symbol = "btcusdt";
        GetMovingAveragePriceQuery getMovingAveragePriceQuery = new GetMovingAveragePriceQuery(symbol);

        AveragePrice averagePrice = AveragePrice.of(new BigDecimal("50.00"));
        when(movingAveragePort.getMovingAverage(symbol)).thenReturn(averagePrice);

        //when
        AveragePrice result = getMovingAveragePriceService.getMovingAverage(getMovingAveragePriceQuery);

        //then
        assertThat(result).isEqualTo(averagePrice);
        verify(movingAveragePort).getMovingAverage(symbol);
    }
}