package org.tss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BankingServiceTestAI { private BankingService bankingService;

    @BeforeEach
    void setUp() {
        bankingService = new BankingService();
    }

    @Test
    void testValidateIban_Valid() {
        // IBAN valid
        assertTrue(bankingService.validateIban("BANK1234RO00000001"));
    }

    @Test
    void testValidateIban_InvalidCountry() {
        // IBAN cu țară invalidă
        assertFalse(bankingService.validateIban("BANK1234FR00000001"));
    }

    @Test
    void testValidateIban_InvalidCurrency() {
        // IBAN cu cod de monedă invalid
        assertFalse(bankingService.validateIban("BANK9999RO00000001"));
    }

    @Test
    void testValidateIban_InvalidLength() {
        // IBAN cu lungime incorectă
        assertFalse(bankingService.validateIban("BANK123RO00000001"));
    }

    @Test
    void testAtm_Check_ValidAccount() {
        // Verificăm dacă acțiunea "check" returnează corect detaliile contului
        String result = bankingService.atm("BANK1234RO00000001", "check", Optional.empty(), Optional.empty());
        assertTrue(result.contains("The account for iban - BANK1234RO00000001, has a balance of 2000 RON"));
    }

    @Test
    void testAtm_Check_InvalidIban() {
        // IBAN invalid
        String result = bankingService.atm("BANK1234XX00000001", "check", Optional.empty(), Optional.empty());
        assertEquals("Invalid IBAN", result);
    }

    @Test
    void testAtm_Withdraw_SufficientBalance() {
        // Verificăm retragerea unei sume dintr-un cont cu suficient sold
        String result = bankingService.atm("BANK1234RO00000001", "withdraw", Optional.of(500), Optional.empty());
        assertTrue(result.contains("You have withdraw 500. Your current balance is 1500"));
    }

    @Test
    void testAtm_Withdraw_InsufficientFunds() {
        // Verificăm retragerea unei sume mai mari decât soldul
        String result = bankingService.atm("BANK1234RO00000001", "withdraw", Optional.of(3000), Optional.empty());
        assertTrue(result.contains("Insufficient funds for withdrawal"));
    }

    @Test
    void testAtm_Withdraw_InvalidAmount() {
        // Verificăm retragerea cu o sumă invalidă
        String result = bankingService.atm("BANK1234RO00000001", "withdraw", Optional.of(-500), Optional.empty());
        assertEquals("Error on account withdrawal", result);
    }

    @Test
    void testAtm_Deposit_ValidCurrency() {
        // Depunere într-o monedă corectă
        String result = bankingService.atm("BANK1234RO00000001", "deposit", Optional.of(500), Optional.of("RON"));
        assertTrue(result.contains("You have deposited 500 RON. Your current balance is 2500"));
    }

    @Test
    void testAtm_Deposit_ExchangeCurrency() {
        // Depunere cu schimb de valută
        String result = bankingService.atm("BANK5678DE00000001", "deposit", Optional.of(1000), Optional.of("RON"));
        assertTrue(result.contains("You have deposited 200 RON. Your current balance is 1700"));
    }

    @Test
    void testAtm_Deposit_InvalidCurrency() {
        // Depunere cu o valută incorectă
        String result = bankingService.atm("BANK5678DE00000001", "deposit", Optional.of(1000), Optional.of("USD"));
        assertEquals("No possible exchange", result);
    }

    @Test
    void testAtm_InvalidAction() {
        // Acțiune invalidă
        String result = bankingService.atm("BANK1234RO00000001", "transfer", Optional.empty(), Optional.empty());
        assertEquals("Invalid action", result);
    }

    @Test
    void testAtm_NoAmountSpecified() {
        // Suma nu este specificată la retragere
        String result = bankingService.atm("BANK1234RO00000001", "withdraw", Optional.empty(), Optional.empty());
        assertEquals("No amount specified", result);
    }

    @Test
    void testAtm_NoCurrencySpecified() {
        // Valuta nu este specificată la depunere
        String result = bankingService.atm("BANK1234RO00000001", "deposit", Optional.of(500), Optional.empty());
        assertEquals("No amount/currency specified", result);
    }
}