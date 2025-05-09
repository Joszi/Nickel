package bosek.joachim.crypto.adapter.in.web;

import bosek.joachim.crypto.application.port.in.StorePriceCommand;
import bosek.joachim.crypto.application.port.in.StorePriceUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class BinanceTradeEventHandlerTest {

    private final StorePriceUseCase storePriceUseCase = Mockito.mock(StorePriceUseCase.class);

    private final BinanceTradeEventHandler binanceTradeEventHandler = new BinanceTradeEventHandler(storePriceUseCase);

    @Test
    void shouldHandleEventAndStorePrice() {
        //given
        String symbol = "btcusdt";
        String json = """
                    {
                      "p": "43200.15",
                      "T": 1678881234567
                    }
                """;

        //when
        binanceTradeEventHandler.handleEvent(symbol, json);

        //then
        ArgumentCaptor<StorePriceCommand> captor = ArgumentCaptor.forClass(StorePriceCommand.class);
        verify(storePriceUseCase).storePrice(captor.capture());

        StorePriceCommand command = captor.getValue();
        assertThat(command.symbol()).isEqualTo(symbol);
        assertThat(command.price()).isEqualTo(new BigDecimal("43200.15"));
        assertThat(command.timestamp()).isEqualTo(Instant.ofEpochMilli(1678881234567L));
    }
}