package bosek.joachim.crypto.application.domain.service;

import bosek.joachim.crypto.application.domain.model.AveragePrice;
import bosek.joachim.crypto.application.domain.model.Price;
import bosek.joachim.crypto.application.port.in.CalculateMovingAverageCommand;
import bosek.joachim.crypto.application.port.out.MovingAveragePort;
import bosek.joachim.crypto.application.port.out.StorePricePort;
import bosek.joachim.crypto.config.CryptoConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CalculateMovingAverageServiceTest {

    private static final String SYMBOL = "btcusdt";
    private static final Instant NOW = Instant.now();
    private static final Duration WINDOW = Duration.ofSeconds(10);

    private final StorePricePort storePricePort = Mockito.mock(StorePricePort.class);
    private final MovingAveragePort movingAveragePort = Mockito.mock(MovingAveragePort.class);

    private final CalculateMovingAverageService calculateMovingAverageService =
            new CalculateMovingAverageService(storePricePort, movingAveragePort, cryptoConfiguration());

    @Test
    void shouldCalculateMovingAverageForOnePrice() {
        //given
        CalculateMovingAverageCommand calculateMovingAverageCommand = new CalculateMovingAverageCommand(SYMBOL);
        when(storePricePort.getRecentPrices(eq(SYMBOL), eq(WINDOW), any(Instant.class)))
                .thenReturn(List.of(Price.of(SYMBOL, new BigDecimal("15.25"), NOW)));

        //when
        calculateMovingAverageService.calculateMovingAverage(calculateMovingAverageCommand);

        //then
        verify(storePricePort).getRecentPrices(eq(SYMBOL), eq(WINDOW), any(Instant.class));
        verify(movingAveragePort).storeMovingAverage(eq(SYMBOL), eq(AveragePrice.of(new BigDecimal("15.25"))));
    }

    @Test
    void shouldCalculateMovingAverageForMultiplePrices() {
        //given
        CalculateMovingAverageCommand calculateMovingAverageCommand = new CalculateMovingAverageCommand(SYMBOL);
        when(storePricePort.getRecentPrices(eq(SYMBOL), eq(WINDOW), any(Instant.class)))
                .thenReturn(List.of(
                        Price.of(SYMBOL, new BigDecimal("15.25"), NOW),
                        Price.of(SYMBOL, new BigDecimal("17.25"), NOW.minusSeconds(1)),
                        Price.of(SYMBOL, new BigDecimal("21.26"), NOW.minusSeconds(2))));

        //when
        calculateMovingAverageService.calculateMovingAverage(calculateMovingAverageCommand);

        //then
        verify(storePricePort).getRecentPrices(eq(SYMBOL), eq(WINDOW), any(Instant.class));
        verify(movingAveragePort).storeMovingAverage(eq(SYMBOL), eq(AveragePrice.of(new BigDecimal("17.92"))));
    }

    @Test
    void shouldCalculateMovingAverageWithNoPrice() {
        //given
        CalculateMovingAverageCommand calculateMovingAverageCommand = new CalculateMovingAverageCommand(SYMBOL);
        when(storePricePort.getRecentPrices(eq(SYMBOL), eq(WINDOW), any(Instant.class))).thenReturn(List.of());

        //when
        calculateMovingAverageService.calculateMovingAverage(calculateMovingAverageCommand);

        //then
        verify(storePricePort).getRecentPrices(eq(SYMBOL), eq(WINDOW), any(Instant.class));
        verify(movingAveragePort).storeMovingAverage(eq(SYMBOL), eq(AveragePrice.of(BigDecimal.ZERO)));
    }

    @Test
    void shouldThrowUnknownSymbolExceptionWhenCalculateMovingAverageWithWrongSymbol() {
        //given
        CalculateMovingAverageCommand calculateMovingAverageCommand = new CalculateMovingAverageCommand("WRONG_SYMBOL");
        when(storePricePort.getRecentPrices(eq(SYMBOL), eq(WINDOW), any(Instant.class))).thenReturn(List.of());

        //when //then
        assertThatExceptionOfType(UnknownSymbolException.class)
                .isThrownBy(() -> calculateMovingAverageService.calculateMovingAverage(calculateMovingAverageCommand))
                .withMessage("Unknown symbol: WRONG_SYMBOL");
    }

    private CryptoConfiguration cryptoConfiguration() {
        CryptoConfiguration cryptoConfiguration = new CryptoConfiguration();
        cryptoConfiguration.setPairs(Map.of(SYMBOL, WINDOW));
        return cryptoConfiguration;
    }
}