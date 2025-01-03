package org.example.fraud.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SuspiciousStatusTest {
    @Test
    public void testSuspiciousStatus() {
        SuspiciousStatus suspiciousStatus = SuspiciousStatus.NORMAL;
        SuspiciousStatus suspiciousStatus1 = SuspiciousStatus.SUSPICIOUS;
        Assertions.assertEquals(SuspiciousStatus.NORMAL, suspiciousStatus);
        Assertions.assertEquals(SuspiciousStatus.SUSPICIOUS, suspiciousStatus1);
    }
}
