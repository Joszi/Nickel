package bosek.joachim.crypto.adapter.in.web;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TradeEventParserTest {

    @Test
    void shouldParseValidJson() {
        //given
        String symbol = "btcusdt";
        BigDecimal price = new BigDecimal("27.45");
        Instant timestamp = Instant.ofEpochMilli(1715270400000L);
        String json = """
                {
                    "p": "27.45",
                    "T": 1715270400000
                }
                """;

        //when
        TradeEventParser.TradeEvent event = TradeEventParser.parse(symbol, json);

        //then
        assertThat(event.symbol()).isEqualTo(symbol);
        assertThat(event.price()).isEqualTo(price);
        assertThat(event.timestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenInvalidJson() {
        //given
        String symbol = "btcusdt";
        String invalidJson = "{ invalid json }";

        //when //then
        assertThatThrownBy(() -> TradeEventParser.parse(symbol, invalidJson))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Failed to parse trade event");
    }

    @Test
    void shouldIgnoreUnknownFields() {
        //given
        String symbol = "btcusdt";
        Instant timestamp = Instant.ofEpochMilli(1715270400000L);
        String jsonWithExtraFields = """
                {
                    "p": "0.125",
                    "T": 1715270400000,
                    "extra": "value"
                }
                """;

        //when
        TradeEventParser.TradeEvent event = TradeEventParser.parse(symbol, jsonWithExtraFields);

        //then
        assertThat(event.symbol()).isEqualTo(symbol);
        assertThat(event.price()).isEqualTo("0.125");
        assertThat(event.timestamp()).isEqualTo(timestamp);
    }
}