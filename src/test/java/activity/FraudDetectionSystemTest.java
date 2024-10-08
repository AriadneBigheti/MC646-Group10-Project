package activity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import activity.FraudDetectionSystem.FraudCheckResult;
import activity.FraudDetectionSystem.Transaction;

public class FraudDetectionSystemTest {

    private FraudDetectionSystem sut;

    @BeforeEach
    public void setUp() {
        sut = new FraudDetectionSystem();
    }

    @Test
    void testHighAmountTransaction() {
        // given
        Transaction transaction = new Transaction(11000, LocalDateTime.now(), "Campinas");
        List<Transaction> transactionsList = new ArrayList<>();
        List<String> blackList = new ArrayList<>();

        // when
        FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertTrue(result.isFraudulent);
        assertTrue(result.verificationRequired);
        assertEquals(50, result.riskScore);
    }

    @Test
    void testExcessiveTransactionsWithinLastHour() {
        // given
        Transaction transaction = new Transaction(100, LocalDateTime.now(), "Campinas");
        List<String> blackList = new ArrayList<>();
        List<Transaction> transactionsList = new ArrayList<>();
    
        for (int i = 0; i < 11; i++) {
            transactionsList.add(new Transaction(50, LocalDateTime.now().minusMinutes(i+10), "Campinas"));
        }

        // when
        FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertTrue(result.isBlocked);
        assertEquals(30, result.riskScore);

    }

    @Test
    void testLocationChangeWithinShortTime() {
        // given
        Transaction transaction = new Transaction(100, LocalDateTime.now(), "Campinas");
        List<Transaction> transactionsList = new ArrayList<>();
        transactionsList.add(new Transaction(100, LocalDateTime.now().minusMinutes(10), "Sao Paulo"));
        List<String> blackList = new ArrayList<>();

        // when
        FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertTrue(result.isFraudulent);
        assertTrue(result.verificationRequired);
        assertEquals(20, result.riskScore);
    }

    @Test
    void testTransactionInBlacklistedLocation() {
        // given
        Transaction transaction = new Transaction(100, LocalDateTime.now(), "Campinas");
        List<Transaction> transactionsList = new ArrayList<>();
        List<String> blackList = new ArrayList<>();
        blackList.add("Campinas");

        // when
        FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertTrue(result.isBlocked);
        assertEquals(100, result.riskScore);
    }

    @Test
    void testNormalTransaction() {
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction(500, now, "Campinas");
        List<Transaction> transactionsList = new ArrayList<>();
        transactionsList.add(new Transaction(1000, now.minusHours(3), "Campinas"));
        List<String> blackList = new ArrayList<>();
        blackList.add("Sao Paulo");

        // when
        FraudDetectionSystem.FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertFalse(result.isFraudulent);
        assertFalse(result.isBlocked);
        assertFalse(result.verificationRequired);
        assertEquals(0, result.riskScore);
    }
}
