package bosek.joachim.crypto.application.domain.model;

import bosek.joachim.crypto.common.CryptoConstants;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.NavigableMap;
import java.util.TreeMap;

@RequiredArgsConstructor
public class MovingAverageCalculator {

    private final Duration window;
    private final NavigableMap<Instant, Price> prices = new TreeMap<>();

    public static MovingAverageCalculator of(Duration window) {
        return new MovingAverageCalculator(window);
    }

    public void addPrice(Price price) {
        prices.put(price.timestamp(), price);
    }

    public AveragePrice getAverage(Instant now) {
        Instant threshold = now.minus(window);
        prices.headMap(threshold, false).clear();

        if (prices.isEmpty()) {
            return AveragePrice.of(BigDecimal.ZERO);
        }

        return calculateAveragePrice();
    }

    private AveragePrice calculateAveragePrice() {
        BigDecimal sum = prices.values().stream()
                .map(Price::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avg = sum.divide(BigDecimal.valueOf(prices.size()), CryptoConstants.SCALE, CryptoConstants.ROUNDING_MODE);

        return AveragePrice.of(avg);
    }
}