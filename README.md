Proiectul realizat de Potlog Lorena-Elena.
# Testare unitară în Java

Proiectul implementează o aplicație de banking care permite diferite acțiuni asupra unor conturi validate prin IBAN.

Am folosit JUnit 5 pentru scrierea testelor funcționale și structurale și Plugin-ul Pitest 1.19.0 pentru obținerea raportului creat de generatorul de mutanți

## Funcționalități

### ATM
---
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

![image](https://github.com/user-attachments/assets/726bce33-dc95-47ca-afd7-21209da2b004)

![image](https://github.com/user-attachments/assets/232c8c40-8f5c-4f23-9067-3acb2e8e21fa)


### CHECK
---
```
public String checkAccount(Account account)
```
Metoda primește ca parametru contul care este verificat. Va afișa IBAN-ul asociat, suma curentă și moneda sumei.

### WITHDRAW
---
```
public String withdraw(Account account, int amount)
```
Metoda primește 2 parametrii:
- *account*: contul din care se va face retragerea
- *amount*: suma care va fi retrasă

![image](https://github.com/user-attachments/assets/a50646c8-ef39-43b9-90e8-82beb126df4d)


### DEPOSIT
---
```
public String deposit(Account account, int amount, String currency)
```
Metoda primește 3 parametrii:
- *account*: contul în care se va face depunerea
- *amount*: suma care va fi depusă
- *currency*: moneda sumei care va fi depusă

Dacă moneda este diferită de moneda contului, atunci prima data se va efectua operația de **exchange** urmată de depunere.

![image](https://github.com/user-attachments/assets/e1cce0b7-d3fe-4895-93c4-34f6d3919526)


## Teste funcționale și structurale
***✅Tests passed: 22 of 22 tests - 147 ms**
---

![image](https://github.com/user-attachments/assets/438989f3-68a4-476d-b440-00a5185c75d9)


**Coverage**
---
![image](https://github.com/user-attachments/assets/6b22b4f9-fcfb-4328-80df-e410f516df51)

**100% pe metode, 100% pe linii și 95% pe ramuri (branch)**

### Testare funcțională - Partiționare de echivalență (metoda validateIban)
1) **Domeniul de intrări**

IBAN - un sir de caractere. Pentru a fi valid, IBAN-ul trebuie să respecte următoarele condiții:
- sa aibă exact 18 caractere
- 0-4 = "BANK"
- 4-8 = un cod ce reprezintă moneda ("1234")
- 8-10 = cod ce reprezintă țara ("RO")
- 10-16 = 6 caractere
- 16-18 = "01"

Distingem următoarele **clase de echivalență**

- N_1 = 18 caractere
- N_2 = mai puțin de 18
- N_3 = mai mult de 18
---
- P_1 = "BANK"
- P_2 = orice altă valoare
---
- M_1 = cod monedă valid
- M_2 = cod monedă invalid
---

- O_1 = cod țară valid
- O_2 = cod țară invalid
---

- Q_1 = "01"
- Q_2 = orice altă valoare
---

2) **Domeniul de ieșiri**
- True
- False

Astfel, împărțim domeniul de intrare în 2: 

- C_1 = {iban-ul este valid -> **True**}
- C_2 = {iban-ul este invalid -> **False**}

![image](https://github.com/user-attachments/assets/16a6e130-aaa2-4a5e-ad92-5b524be39857)


### Testare funcțională - Valori de frontieră (metoda withdraw)
Suma care poate fi retrasă este între 0 și suma disponibilă în cont. Testăm 4 valori de frontieră:
- o valoarea negativă
- 0
- suma disponibilă în cont
- o valoare mai mare decât suma disponibilă în cont
    
![image](https://github.com/user-attachments/assets/fefe97ed-18ac-4844-ad22-15b1c908ade2)


### Testare structurală - Acoperire la nivel de instrucțiune
![image](https://github.com/user-attachments/assets/6b5ec312-5bb5-4a77-a900-e5c1017e546d)

Pentru metoda **exhange** folosită de metoda **deposit** există o instrucțiune de tipul **if - else if**.
Testăm ambele noduri, dând valori adevarăte pentru condiția din if și apoi pentru cea din else if.

![image](https://github.com/user-attachments/assets/97d5c95d-d0df-44bf-ad4e-6aac7a654c09)

| Metodă                     | Account             | Amount | Currency | Rezultat                                                                | Decizii acoperite |
|--------------------------------|---------------------|--------|----------|-------------------------------------------------------------------------|-------------------|
| `depositAmountExchangeToEuro` | validAccount in EUR | 350    | RON      | "You have deposited 70 EUR. Your current balance is 570"                | IF                |
| `depositAmountExchangeToRon`  | validAccount in RON | 350    | EUR      | "You have deposited 1753 RON. Your current balance is 2253"             | ELSE              |

### Testare structurală - Acoperire la nivel de ramură

![image](https://github.com/user-attachments/assets/ebc6adcd-82c9-4501-80cd-9a7f8b09e8b7)

![image](https://github.com/user-attachments/assets/4477c8b0-8bd5-4713-9c6e-09ec8df0ef53)

![image](https://github.com/user-attachments/assets/d204a1e5-ceed-4927-8d20-b9ff1ac9a1c3)

#### Testăm fiecare ramură din metoda **deposit**.
---
![image](https://github.com/user-attachments/assets/2d6f19da-6408-4b8b-b024-921b96326be5)

| Metodă                       | Account                | Amount | Currency | Rezultat                                                                 | Decizii acoperite             |
|----------------------------------|------------------------|--------|----------|--------------------------------------------------------------------------|-------------------------------|
| `depositAmountNoExchangeFound`  | validAccount in RON    | 350    | USD      | "No possible exchange"                                                   | B1 – if false, if true        |
| `depositAmountExchangeToRon`    | validAccount in RON    | 350    | EUR      | "You have deposited 1753 RON. Your current balance is 2253"              | B2 – if true                  |
| `depositAmount`                 | validAccount in RON    | 350    | RON      | "You have deposited 400 RON. Your current balance is 2400"               | B3 – if false, if false       |


### Testare structurală - Acoperire la nivel de condiție

![image](https://github.com/user-attachments/assets/4ceba143-3914-4f37-b0c2-2032eb9556e8)

Pentru metoda **atm** testăm condițiile if, pentru validitatea IBAN-ului (C1) și pentru găsirea contului specific IBAN-ului (C2), dând atăt valori false și true.

![image](https://github.com/user-attachments/assets/f10eca15-3d04-46cd-8d28-f35ca3f7229b)

- Test 1 (atmInvalidIban): C1 - false
- Test 2 (atmNoAccountFound): C1 - true, C2 - false
- Test 3 (atmCheck): C2 - true
  
| Metodă        | IBAN                          | Action | Amount | Currency | Rezultat                                                                 | Decizii acoperite           |
|---------------------|-------------------------------|--------|--------|----------|--------------------------------------------------------------------------|-----------------------------|
| `atmInvalidIban`    | invalid iban                  | check  | 1000   | RON      | "Invalid IBAN"                                                            | C1 – false                  |
| `atmNoAccountFound` | valid iban for invalid account| check  | 1000   | RON      | "Account not found"                                                      | C1 – true, C2 – false       |
| `atmCheck`          | valid iban                    | check  | 1000   | RON      | "The account for iban - BANK1234RO00000001, has a balance of 2000 RON"   | C2 – false                  |

## Raport Pitest (Mutation testing)
**Primul raport generat**

Mutation Coverage -> 49/52

![image](https://github.com/user-attachments/assets/89b0a454-1cd9-4278-8532-00159c4c2ae3)

**Analiză**

![image](https://github.com/user-attachments/assets/fd5480c6-9751-4127-a846-1284aa8c35ca)

![image](https://github.com/user-attachments/assets/34eb3301-e694-465b-967d-3d8f78e8515f)

Raportul arată că pentru metoda **withdraw** există 2 mutații care au supraviețuit, mai precis ele nu au fost detectate de testele unitare.

**Modificări**

Metoda inițială

![image](https://github.com/user-attachments/assets/c9258663-df98-4074-9e48-59b8fe609a12)

Metoda îmbunătățită

![image](https://github.com/user-attachments/assets/79937602-336f-41e9-bf59-ea80391287d5)

Teste suplimentare

![image](https://github.com/user-attachments/assets/bccb6002-d872-4082-8c21-81a72508a6be)


**Al doilea raport generat**

Mutation Coverage -> 52/52

![image](https://github.com/user-attachments/assets/d5c905bf-4f5e-4846-965f-b02abd7aea77)

## Raport AI

![image](https://github.com/user-attachments/assets/6c4ca4b8-cf9d-459f-ba4f-07fd7039d1cc)
![image](https://github.com/user-attachments/assets/590adbde-7564-4bae-8af6-27f9098b31db)
![image](https://github.com/user-attachments/assets/c4e20b10-defc-4f4e-9d86-aa2fac875240)

---
![image](https://github.com/user-attachments/assets/183c5037-a0d7-43fb-b0c4-2ce871394c16)

- Testul invalid nu este complet greșit. El testează corect logica ,însă în mesajul rezultat există o confuzie generată de moneda în care este afișată suma. În schimb valorile din rezultat, pentru sumă și total, sunt corecte.
---
*Analiză*

- ChatGPT a generat 15 teste unitare, dintre care 14 au fost valide. Coverage-ul este de 100% pe metode, 94% pe linii și 75% pe ramuri (branch).
- Per total testele acoperă majoritatea codului, dar anumite condiții au fost testate cu o singură valoare (true), în loc de ambele posibile (true și false).
- Prin comparație, am reușit să găsesc redundanțe în testele scrise inițial și astfel am redus numărul lor de la 25 la 22 fără a modifica coverage-ul de 100%.



## Configurare
- **IDE** - Intelij IDEA
- **Java** - 22
- **Maven**
- **JUnit** - 5.7.1
- **Pitest** - 1.19.0
  
## Surse
- OpenAI, ChatGPT, https://chatgpt.com/, Data generării: 30 martie 2025


