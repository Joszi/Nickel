package bosek.joachim.crypto.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;

public record StorePriceCommand(String symbol, BigDecimal price, Instant timestamp) {
}
