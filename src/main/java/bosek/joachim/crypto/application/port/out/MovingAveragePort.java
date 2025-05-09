package bosek.joachim.crypto.application.port.out;

import bosek.joachim.crypto.application.domain.model.AveragePrice;

public interface MovingAveragePort {
    void storeMovingAverage(String symbol, AveragePrice averagePrice);

    AveragePrice getMovingAverage(String symbol);
}