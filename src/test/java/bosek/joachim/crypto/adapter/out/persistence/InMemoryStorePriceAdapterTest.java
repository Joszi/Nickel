package bosek.joachim.crypto.adapter.out.persistence;

import bosek.joachim.crypto.application.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryStorePriceAdapterTest {

    private final InMemoryStorePriceAdapter adapter = new InMemoryStorePriceAdapter();

    @Test
    void shouldStorePriceForGivenSymbol() {
        //given
        String symbol = "btcusdt";
        Price price = Price.of(symbol, new BigDecimal("30.00"), Instant.now());

        //when
        adapter.storePrice(symbol, price);

        //then
        List<Price> prices = adapter.getRecentPrices(symbol, Duration.ofSeconds(5), Instant.now());
        assertThat(prices).containsExactly(price);
    }

    @Test
    void shouldFilterPricesOutsideWindow() {
        //given
        String symbol = "btcusdt";
        Instant now = Instant.now();
        Duration window = Duration.ofSeconds(10);

        Price oldPrice = Price.of(symbol, new BigDecimal("20.00"), now.minus(Duration.ofMinutes(50)));
        Price recentPrice = Price.of(symbol, new BigDecimal("21.00"), now.minusSeconds(3));

        adapter.storePrice(symbol, oldPrice);
        adapter.storePrice(symbol, recentPrice);

        //when
        List<Price> prices = adapter.getRecentPrices(symbol, window, now);

        //then
        assertThat(prices).containsExactly(recentPrice);
    }

    @Test
    void shouldReturnEmptyListWhenNoPricesForSymbol() {
        //given //when
        List<Price> prices = adapter.getRecentPrices("UNKNOWN", Duration.ofMinutes(1), Instant.now());

        //then
        assertThat(prices).isEmpty();
    }
}