package bosek.joachim.crypto.application.domain.service;

import bosek.joachim.crypto.application.domain.model.Price;
import bosek.joachim.crypto.application.port.in.StorePriceCommand;
import bosek.joachim.crypto.application.port.in.StorePriceUseCase;
import bosek.joachim.crypto.application.port.out.StorePricePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class StorePriceService implements StorePriceUseCase {

    private final StorePricePort storePricePort;

    @Override
    public void storePrice(StorePriceCommand storePriceCommand) {
        String symbol = storePriceCommand.symbol();
        log.info("Stored spot price for symbol: {} -> {}", symbol, storePriceCommand.price());
        storePricePort.storePrice(symbol, Price.of(symbol, storePriceCommand.price(), storePriceCommand.timestamp()));
    }
}
