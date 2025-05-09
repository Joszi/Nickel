package bosek.joachim.crypto.adapter.out.persistence;

import bosek.joachim.crypto.application.domain.model.AveragePrice;
import bosek.joachim.crypto.application.port.out.MovingAveragePort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
class InMemoryMovingAverageAdapter implements MovingAveragePort {

    private final Map<String, AveragePrice> storage = new ConcurrentHashMap<>();

    @Override
    public void storeMovingAverage(String symbol, AveragePrice averagePrice) {
        storage.put(symbol, averagePrice);
    }

    @Override
    public AveragePrice getMovingAverage(String symbol) {
        return storage.getOrDefault(symbol, AveragePrice.of(BigDecimal.ZERO));
    }
}
