package bosek.joachim.crypto.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TradeEventParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    static TradeEvent parse(String symbol, String json) {
        try {
            TradePayload tradePayload = mapper.readValue(json, TradePayload.class);
            return new TradeEvent(symbol, tradePayload.price(), Instant.ofEpochMilli(tradePayload.eventTime()));
        } catch (JacksonException e) {
            throw new IllegalArgumentException("Failed to parse trade event", e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record TradePayload(@JsonProperty("p") BigDecimal price,
                        @JsonProperty("T") long eventTime) {
    }

    record TradeEvent(String symbol, BigDecimal price, Instant timestamp) {
    }
}