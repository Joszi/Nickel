package bosek.joachim.crypto.application.domain.model;

import bosek.joachim.crypto.common.CryptoConstants;

import java.math.BigDecimal;

public record AveragePrice(BigDecimal average) {

    public static AveragePrice of(BigDecimal average) {
        BigDecimal avg = average.setScale(CryptoConstants.SCALE, CryptoConstants.ROUNDING_MODE);
        return new AveragePrice(avg);
    }
}