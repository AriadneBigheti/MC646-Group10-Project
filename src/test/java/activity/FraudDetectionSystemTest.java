package activity;

import org.junit.Before;
import org.junit.Test;

import activity.FraudDetectionSystem.FraudCheckResult;
import activity.FraudDetectionSystem.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;


public class FraudDetectionSystemTest {

    private FraudDetectionSystem sut;

    @Before
    public void setUp() {
        sut = new FraudDetectionSystem();
    }

    @Test
    public void TC1() {
        // given
        Transaction transaction = new Transaction(11000, LocalDateTime.now(), "Campinas");
        List<Transaction> transactionsList = new ArrayList<>();
        List<String> blackList = new ArrayList<>();

        // when
        FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertTrue(result.verificationRequired);
        assertTrue(result.riskScore >= 50);
        assertTrue(result.riskScore <= 100);
    }

    @Test
    public void TC2() {
        // given
        Transaction transaction = new Transaction(100, LocalDateTime.now(), "Campinas");
        List<String> blackList = new ArrayList<>();
        List<Transaction> transactionsList = new ArrayList<>();
    
        for (int i = 0; i < 11; i++) {
            transactionsList.add(new Transaction(50, LocalDateTime.now(), "Campinas"));
        }

        // when
        FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertTrue(result.isBlocked);
        assertTrue(result.riskScore >= 30);
        assertTrue(result.riskScore <= 100);
    }

    @Test
    public void TC3() {
        // given
        Transaction transaction = new Transaction(100, LocalDateTime.now(), "Campinas");
        List<Transaction> transactionsList = new ArrayList<>();
        transactionsList.add(new Transaction(100, LocalDateTime.now(), "Sao Paulo"));
        List<String> blackList = new ArrayList<>();

        // when
        FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertTrue(result.verificationRequired);
        assertTrue(result.riskScore >= 20);
        assertTrue(result.riskScore <= 100);
    }

    @Test
    public void TC4() {
        // given
        Transaction transaction = new Transaction(100, LocalDateTime.now(), "Campinas");
        List<Transaction> transactionsList = new ArrayList<>();
        List<String> blackList = new ArrayList<>();
        blackList.add("Campinas");

        // when
        FraudCheckResult result = sut.checkForFraud(transaction, transactionsList, blackList);

        // then
        assertTrue(result.isBlocked);
        assertTrue(result.riskScore == 100); 
    }


}
