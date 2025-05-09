package bosek.joachim.crypto.application.domain.service;

import bosek.joachim.crypto.application.domain.model.Price;
import bosek.joachim.crypto.application.port.in.StorePriceCommand;
import bosek.joachim.crypto.application.port.out.StorePricePort;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class StorePriceServiceTest {

    private final StorePricePort storePricePort = Mockito.mock(StorePricePort.class);

    private final StorePriceService storePriceService = new StorePriceService(storePricePort);

    @Test
    void shouldStorePriceWithCorrectArguments() {
        //given
        String symbol = "btcusdt";
        BigDecimal price = new BigDecimal("1.23");
        Instant now = Instant.now();

        StorePriceCommand storePriceCommand = new StorePriceCommand(symbol, price, now);

        //when
        storePriceService.storePrice(storePriceCommand);

        //then
        ArgumentCaptor<Price> priceCaptor = ArgumentCaptor.forClass(Price.class);
        verify(storePricePort).storePrice(eq(symbol), priceCaptor.capture());
    }
}