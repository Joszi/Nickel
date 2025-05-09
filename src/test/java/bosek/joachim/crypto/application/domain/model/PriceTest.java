package bosek.joachim.crypto.application.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void shouldCreatePriceCorrectly() {
        //given
        String symbol = "btc";
        BigDecimal price = new BigDecimal("200.00");
        Instant timestamp = Instant.now();

        //when
        Price result = Price.of(symbol, price, timestamp);

        //then
        assertThat(result.symbol()).isEqualTo(symbol);
        assertThat(result.price()).isEqualTo(price);
        assertThat(result.timestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldCreatePriceWithNullTimestamp() {
        //given
        String symbol = "btc";
        BigDecimal price = new BigDecimal("10000.00");

        //when
        Price result = Price.of(symbol, price, null);

        //then
        assertThat(result.symbol()).isEqualTo(symbol);
        assertThat(result.price()).isEqualTo(price);
        assertThat(result.timestamp()).isNull();
    }

    @Test
    void shouldPricesNotBeEqualWhenDifferentSymbols() {
        //given
        String symbol1 = "btc";
        String symbol2 = "eth";
        BigDecimal price = new BigDecimal("200.00");
        Instant timestamp = Instant.now();

        //when
        Price price1 = Price.of(symbol1, price, timestamp);
        Price price2 = Price.of(symbol2, price, timestamp);

        //then
        assertThat(price1).isNotEqualTo(price2);
    }

    @Test
    void shouldHandleNullPrice() {
        //given
        String symbol = "btc";
        Instant timestamp = Instant.now();

        //when
        Price result = Price.of(symbol, null, timestamp);

        //then
        assertThat(result.symbol()).isEqualTo(symbol);
        assertThat(result.price()).isNull();
        assertThat(result.timestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldHandlePriceWithZeroValue() {
        //given
        String symbol = "btc";
        BigDecimal price = BigDecimal.ZERO;
        Instant timestamp = Instant.now();

        //when
        Price result = Price.of(symbol, price, timestamp);

        //then
        assertThat(result.symbol()).isEqualTo(symbol);
        assertThat(result.price()).isEqualTo(BigDecimal.ZERO);
        assertThat(result.timestamp()).isEqualTo(timestamp);
    }
}