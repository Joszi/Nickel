package bosek.joachim.crypto.application.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Price(String symbol, BigDecimal price, Instant timestamp) {

    public static Price of(String symbol, BigDecimal price, Instant timestamp) {
        return new Price(symbol, price, timestamp);
    }
}
