package bosek.joachim.crypto.application.domain.service;

import bosek.joachim.crypto.application.domain.model.AveragePrice;
import bosek.joachim.crypto.application.domain.model.MovingAverageCalculator;
import bosek.joachim.crypto.application.domain.model.Price;
import bosek.joachim.crypto.application.port.in.CalculateMovingAverageCommand;
import bosek.joachim.crypto.application.port.in.CalculateMovingAverageUseCase;
import bosek.joachim.crypto.application.port.out.MovingAveragePort;
import bosek.joachim.crypto.application.port.out.StorePricePort;
import bosek.joachim.crypto.config.CryptoConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class CalculateMovingAverageService implements CalculateMovingAverageUseCase {

    private final StorePricePort storePricePort;
    private final MovingAveragePort movingAveragePort;
    private final CryptoConfiguration cryptoConfiguration;

    @Override
    public void calculateMovingAverage(CalculateMovingAverageCommand calculateMovingAverageCommand) {
        String symbol = calculateMovingAverageCommand.symbol();
        Duration window = Optional.ofNullable(cryptoConfiguration.getPairs().get(symbol))
                .orElseThrow(() -> new UnknownSymbolException(symbol));
        Instant now = Instant.now();

        List<Price> prices = storePricePort.getRecentPrices(symbol, window, now);
        MovingAverageCalculator calculator = MovingAverageCalculator.of(window);
        prices.forEach(calculator::addPrice);

        AveragePrice averagePrice = calculator.getAverage(now);

        log.info("Calculated moving average price for symbol: {} -> {}", symbol, averagePrice);
        movingAveragePort.storeMovingAverage(symbol, averagePrice);
    }
}
