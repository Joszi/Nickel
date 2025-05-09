package bosek.joachim.crypto.application.domain.service;

import bosek.joachim.crypto.application.domain.model.AveragePrice;
import bosek.joachim.crypto.application.port.in.GetMovingAveragePriceQuery;
import bosek.joachim.crypto.application.port.in.GetMovingAveragePriceUseCase;
import bosek.joachim.crypto.application.port.out.MovingAveragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetMovingAveragePriceService implements GetMovingAveragePriceUseCase {

    private final MovingAveragePort movingAveragePort;

    @Override
    public AveragePrice getMovingAverage(GetMovingAveragePriceQuery getMovingAveragePriceQuery) {
        return movingAveragePort.getMovingAverage(getMovingAveragePriceQuery.symbol());
    }
}
