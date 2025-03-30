Ilustrați strategiile de generare de teste prezentate la curs (partiționare în clase de
echivalență, analiza valorilor de frontieră, acoperire la nivel de instrucțiune, decizie, condiție,
circuite independente, analiză raport creat de generatorul de mutanți, teste suplimentare pentru a
omorî 2 dintre mutanții neechivalenți rămași în viață) pe exemple proprii.


# Testare unitară în Java

Proiectul implementează o aplicație de banking care permite diferite acțiuni asupra unor conturi validate prin IBAN.

Am folosit JUnit 5 pentru scrierea testelor funcționale și structurale și Plugin-ul Pitest 1.19.0 pentru obținerea raportului creat de generatorul de mutanți

## Funcționalități

#### ATM
```
public void atm(String iban, String action, Optional<Integer> amount, Optional<String> currency) 
```
Metoda primește 4 parametrii și în funcție de anumite validări, redirecționează spre acțiunea corespunzătoare 

**Parametrii**
- *iban*: iban-ul contului asupra căruia aplicăm diferite acțiuni
- *action*: acțiunea dorită asupra contului
- *amount*: suma de bani ce trebuie specificată pentru acțiunile de withdraw și deposit
- *currency*: moneda sumei de bani pentru acțiunea de deposit

**Validări**
- *validateIban*: validăm dacă IBAN-ul respectă formatul cerut - *BANK + 4 digits (currency code) + 2 letters (country code) + 6 digits + 01*
- *getAccountFromIban*: verificăm dacă există un cont cu IBAN-ul respectiv
  
**Acțiuni**
- *check*: afișsare detalii cont
- *withdraw*: retragere bani 
- *deposit*: depozitare bani

#### CHECK
```
public String checkAccount(Account account)
```
Metoda primește ca parametru contul care este verificat. Va afișa IBAN-ul asociat, suma curentă și moneda sumei.

#### WITHDRAW
```
public String withdraw(Account account, int amount)
```
Metoda primește 2 parametrii:
- *account*: contul din care se va face retragerea
- *amount*: suma care va fi retrasă

#### DEPOSIT
```
public String deposit(Account account, int amount, String currency)
```
Metoda primește 3 parametrii:
- *account*: contul în care se va face depunerea
- *amount*: suma care va fi depusă
- *currency*: moneda sumei care va fi depusă

Dacă moneda este diferită de moneda contului, atunci prima data se va efectua operația de **exchange** urmată de depunere.


## Teste funcționale și structurale
**✅Tests passed: 23 of 23 tests - 48 ms**

![image](https://github.com/user-attachments/assets/65da566c-78f1-4eed-ab76-cdf621ea1dec)

**Coverage**

![image](https://github.com/user-attachments/assets/0db85e71-b35e-46d0-a894-0eca5aeaa352)


#### Testare funcțională - Partiționare de echivalență
-> validateIban 
-> deposit
#### Testare funcțională - Valori de frontieră

Suma care poate fi retrasă este între 0 și suma disponibilă în cont. Testăm 4 valori de frontieră:
  - o valoarea negativă
  - 0
  - suma disponibilă în cont
  - o valoare mai mare decât suma disponibilă în cont


#### Testare structurală - Acoperire la nivel de instrucțiune
#### Testare structurală - Acoperire la nivel de decizie
#### Testare structurală - Acoperire la nivel de condiție
#### Testare structurală - Circuite independente


## Raport Pitest (Mutation testing)
![image](https://github.com/user-attachments/assets/89b0a454-1cd9-4278-8532-00159c4c2ae3)

Analiză

## Grafic

## Raport AI

Analiză


## Configurare
- **IDE** - Intelij IDEA
- **Java** - 22 &
- **Maven**
- **JUnit** - 5.7.1
- **Pitest** - 1.19.0



