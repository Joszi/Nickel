package bosek.joachim.crypto.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "crypto")
public class CryptoConfiguration {

    private Map<String, Duration> pairs;
}
