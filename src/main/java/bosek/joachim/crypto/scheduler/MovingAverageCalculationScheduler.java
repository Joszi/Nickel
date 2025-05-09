package bosek.joachim.crypto.scheduler;

import bosek.joachim.crypto.application.port.in.CalculateMovingAverageCommand;
import bosek.joachim.crypto.application.port.in.CalculateMovingAverageUseCase;
import bosek.joachim.crypto.config.CryptoConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MovingAverageCalculationScheduler {

    private final CalculateMovingAverageUseCase calculateMovingAverageUseCase;
    private final CryptoConfiguration cryptoProperties;

    @Scheduled(fixedRateString = "${app.calculationRateMillis:1000}")
    void calculate() {
        cryptoProperties.getPairs().keySet().forEach(symbol -> {
            CalculateMovingAverageCommand calculateMovingAverageCommand = new CalculateMovingAverageCommand(symbol);
            calculateMovingAverageUseCase.calculateMovingAverage(calculateMovingAverageCommand);
        });
    }
}
