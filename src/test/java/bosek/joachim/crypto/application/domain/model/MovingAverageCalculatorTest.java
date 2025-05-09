package bosek.joachim.crypto.application.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class MovingAverageCalculatorTest {

    private static final Duration WINDOW = Duration.ofSeconds(30);

    @Test
    void shouldReturnZeroWhenNoPrices() {
        //given
        MovingAverageCalculator calculator = MovingAverageCalculator.of(WINDOW);
        Instant now = Instant.now();

        //when
        AveragePrice average = calculator.getAverage(now);

        //then
        assertThat(average.average()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void shouldCalculateAverageFromSinglePriceInWindow() {
        //given
        MovingAverageCalculator calculator = MovingAverageCalculator.of(WINDOW);
        Instant now = Instant.now();

        calculator.addPrice(price(new BigDecimal("50.00"), now));

        //when
        AveragePrice average = calculator.getAverage(now);

        //then
        assertThat(average.average()).isEqualTo(new BigDecimal("50.00"));
    }

    @Test
    void shouldCalculateAverageFromMultiplePricesInWindow() {
        //given
        MovingAverageCalculator calculator = MovingAverageCalculator.of(WINDOW);
        Instant now = Instant.now();

        calculator.addPrice(price(new BigDecimal("25"), now.minusSeconds(25)));
        calculator.addPrice(price(new BigDecimal("20"), now.minusSeconds(20)));
        calculator.addPrice(price(new BigDecimal("10"), now.minusSeconds(10)));
        calculator.addPrice(price(new BigDecimal("6"), now));

        //when
        AveragePrice averagePrice = calculator.getAverage(now);

        //then
        assertThat(averagePrice.average()).isEqualTo(new BigDecimal("15.25"));
    }

    @Test
    void shouldCalculateAverageWhenFirstPriceIsOutsideTheWindow() {
        //given
        MovingAverageCalculator calculator = MovingAverageCalculator.of(WINDOW);
        Instant now = Instant.now();

        calculator.addPrice(price(new BigDecimal("35"), now.minusSeconds(35)));
        calculator.addPrice(price(new BigDecimal("20"), now.minusSeconds(20)));
        calculator.addPrice(price(new BigDecimal("10"), now.minusSeconds(10)));
        calculator.addPrice(price(new BigDecimal("6"), now));

        //when
        AveragePrice averagePrice = calculator.getAverage(now);

        //then
        assertThat(averagePrice.average()).isEqualTo(new BigDecimal("12.00"));
    }

    @Test
    void shouldReturnZeroWhenAllPricesAreOutsideTheWindow() {
        //given
        MovingAverageCalculator calculator = new MovingAverageCalculator(WINDOW);
        Instant now = Instant.now();

        calculator.addPrice(price(new BigDecimal("45"), now.minusSeconds(45)));
        calculator.addPrice(price(new BigDecimal("40"), now.minusSeconds(40)));
        calculator.addPrice(price(new BigDecimal("35"), now.minusSeconds(35)));
        calculator.addPrice(price(new BigDecimal("31"), now.minusSeconds(31)));

        //when
        AveragePrice averagePrice = calculator.getAverage(now);

        //then
        assertThat(averagePrice.average()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void shouldCalculateAverageWhenFirstPriceIsEqualToWindow() {
        //given
        MovingAverageCalculator calculator = new MovingAverageCalculator(WINDOW);
        Instant now = Instant.now();

        calculator.addPrice(price(new BigDecimal("30"), now.minusSeconds(30)));
        calculator.addPrice(price(new BigDecimal("20"), now.minusSeconds(20)));
        calculator.addPrice(price(new BigDecimal("10"), now.minusSeconds(10)));
        calculator.addPrice(price(new BigDecimal("3"), now));

        //when
        AveragePrice averagePrice = calculator.getAverage(now);

        //then
        assertThat(averagePrice.average()).isEqualTo(new BigDecimal("15.75"));
    }

    private static Price price(BigDecimal price, Instant timestamp) {
        return new Price("btcusdt", price, timestamp);
    }
}