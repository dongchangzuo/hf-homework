package org.example.fraud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
class FraudDetectionApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures that the Spring application context can load successfully.
    }
}
