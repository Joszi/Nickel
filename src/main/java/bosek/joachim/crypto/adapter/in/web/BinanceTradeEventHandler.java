package bosek.joachim.crypto.adapter.in.web;

import bosek.joachim.crypto.application.port.in.StorePriceCommand;
import bosek.joachim.crypto.application.port.in.StorePriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BinanceTradeEventHandler {

    private final StorePriceUseCase storePriceUseCase;

    void handleEvent(String symbol, String event) {
        TradeEventParser.TradeEvent tradeEvent = TradeEventParser.parse(symbol, event);
        StorePriceCommand storePriceCommand = new StorePriceCommand(tradeEvent.symbol(), tradeEvent.price(), tradeEvent.timestamp());
        storePriceUseCase.storePrice(storePriceCommand);
    }
}