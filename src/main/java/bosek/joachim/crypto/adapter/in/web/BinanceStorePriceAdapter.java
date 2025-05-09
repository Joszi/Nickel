package bosek.joachim.crypto.adapter.in.web;

import bosek.joachim.crypto.config.CryptoConfiguration;
import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class BinanceStorePriceAdapter {

    private final BinanceTradeEventHandler binanceTradeEventHandler;
    private final CryptoConfiguration cryptoConfiguration;
    private final WebSocketStreamClient webSocketStreamClient = new WebSocketStreamClientImpl();
    private final List<Integer> connectionIds = new ArrayList<>();

    @PostConstruct
    void init() {
        cryptoConfiguration.getPairs().keySet().forEach(symbol -> {
            int connectionId = webSocketStreamClient.tradeStream(symbol,
                    event -> binanceTradeEventHandler.handleEvent(symbol, event));
            connectionIds.add(connectionId);
        });
    }

    @PreDestroy
    void shutdown() {
        connectionIds.forEach(webSocketStreamClient::closeConnection);
    }
}