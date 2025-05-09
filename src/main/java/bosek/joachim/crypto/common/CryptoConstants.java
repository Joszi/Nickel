package bosek.joachim.crypto.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CryptoConstants {

    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
}
