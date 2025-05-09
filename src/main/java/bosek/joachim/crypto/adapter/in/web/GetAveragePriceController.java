package bosek.joachim.crypto.adapter.in.web;

import bosek.joachim.crypto.application.domain.model.AveragePrice;
import bosek.joachim.crypto.application.port.in.GetMovingAveragePriceQuery;
import bosek.joachim.crypto.application.port.in.GetMovingAveragePriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GetAveragePriceController {

    private final GetMovingAveragePriceUseCase getMovingAveragePriceUseCase;

    @GetMapping("/api/moving-average/{symbol}")
    ResponseEntity<AveragePrice> getMovingAverage(@PathVariable String symbol) {
        GetMovingAveragePriceQuery getMovingAveragePriceQuery = new GetMovingAveragePriceQuery(symbol);
        AveragePrice averagePrice = getMovingAveragePriceUseCase.getMovingAverage(getMovingAveragePriceQuery);
        return ResponseEntity.ok(averagePrice);
    }
}