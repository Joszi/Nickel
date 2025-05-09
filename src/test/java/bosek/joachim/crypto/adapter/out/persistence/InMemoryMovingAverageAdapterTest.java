package bosek.joachim.crypto.adapter.out.persistence;

import bosek.joachim.crypto.application.domain.model.AveragePrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryMovingAverageAdapterTest {

    private final InMemoryMovingAverageAdapter adapter = new InMemoryMovingAverageAdapter();

    @Test
    void shouldStoreAndRetrieveAveragePriceForSymbol() {
        //given
        String symbol = "btcusdt";
        AveragePrice averagePrice = AveragePrice.of(new BigDecimal("27.50"));

        //when
        adapter.storeMovingAverage(symbol, averagePrice);
        AveragePrice result = adapter.getMovingAverage(symbol);

        //then
        assertThat(result).isEqualTo(averagePrice);
    }

    @Test
    void shouldReturnZeroAveragePriceIfSymbolNotFound() {
        //given //when
        AveragePrice result = adapter.getMovingAverage("UNKNOWN");

        //then
        assertThat(result).isEqualTo(AveragePrice.of(BigDecimal.ZERO));
    }
}