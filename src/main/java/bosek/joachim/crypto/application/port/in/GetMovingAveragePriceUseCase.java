package bosek.joachim.crypto.application.port.in;

import bosek.joachim.crypto.application.domain.model.AveragePrice;

public interface GetMovingAveragePriceUseCase {
    AveragePrice getMovingAverage(GetMovingAveragePriceQuery getMovingAveragePriceQuery);
}
