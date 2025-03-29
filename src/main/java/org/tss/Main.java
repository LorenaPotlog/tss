package org.tss;


//  T3 Testare unitară în Java

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        BankingService bankingService = new BankingService();
        String result1  = bankingService.atm("BANK1234RO00000001", "check", Optional.empty(), Optional.empty());

        String result2 = bankingService.atm("BANK1234RO00000001", "deposit", Optional.of(200), Optional.of("RON"));

        String result3 = bankingService.atm("BANK1234RO00000001", "withdraw", Optional.of(400),Optional.of("RON"));
        String result4 = bankingService.atm("BANK1234RO00000001", "withdraw", Optional.of(2000),Optional.of("RON"));

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);


    }
}