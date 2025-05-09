package bosek.joachim.crypto.adapter.out.persistence;

import bosek.joachim.crypto.application.domain.model.Price;
import bosek.joachim.crypto.application.port.out.StorePricePort;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
class InMemoryStorePriceAdapter implements StorePricePort {

    private final Map<String, List<Price>> priceStorage = new ConcurrentHashMap<>();

    @Override
    public void storePrice(String symbol, Price price) {
        priceStorage.computeIfAbsent(symbol, key -> new CopyOnWriteArrayList<>()).add(price);
    }

    @Override
    public List<Price> getRecentPrices(String symbol, Duration window, Instant now) {
        return priceStorage.getOrDefault(symbol, List.of()).stream()
                .filter(price -> price.timestamp().isAfter(now.minus(window)))
                .toList();
    }
}