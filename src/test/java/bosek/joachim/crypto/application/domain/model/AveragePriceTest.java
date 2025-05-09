package bosek.joachim.crypto.application.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AveragePriceTest {

    @Test
    void shouldCreateAveragePriceWithCorrectScaleAndRounding() {
        //given
        BigDecimal input = new BigDecimal("123.456");

        //when
        AveragePrice result = AveragePrice.of(input);

        //then
        assertThat(result.average()).isEqualTo(new BigDecimal("123.46"));
    }

    @Test
    void shouldHandleZeroCorrectly() {
        //given
        BigDecimal input = BigDecimal.ZERO;

        //when
        AveragePrice result = AveragePrice.of(input);

        //then
        assertThat(result.average()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void shouldPreservePrecisionForSmallNumbers() {
        //given
        BigDecimal input = new BigDecimal("0.007");

        //when
        AveragePrice result = AveragePrice.of(input);

        //then
        assertThat(result.average()).isEqualTo(new BigDecimal("0.01"));
    }

    @Test
    void shouldPreservePrecisionForLargeNumbers() {
        //given
        BigDecimal input = new BigDecimal("1000000.999");

        //when
        AveragePrice result = AveragePrice.of(input);

        //then
        assertThat(result.average()).isEqualTo(new BigDecimal("1000001.00"));
    }
}