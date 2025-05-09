package bosek.joachim.crypto.application.port.out;

import bosek.joachim.crypto.application.domain.model.Price;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public interface StorePricePort {
    void storePrice(String symbol, Price price);

    List<Price> getRecentPrices(String symbol, Duration window, Instant now);
}
