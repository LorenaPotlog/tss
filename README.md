Ilustrați strategiile de generare de teste prezentate la curs (, acoperire la nivel de instrucțiune, decizie, condiție,
circuite independente


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

![image](https://github.com/user-attachments/assets/6c09e61d-4fd4-448e-b6b4-635a93684171)
![image](https://github.com/user-attachments/assets/3e97138a-f456-4616-b96a-eadf4db90602)

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

![image](https://github.com/user-attachments/assets/c4757c82-d2f9-4fc4-8162-d3e0b342dd48)


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

![image](https://github.com/user-attachments/assets/d09f5845-4416-4f88-b628-5e1e93872946)


## Teste funcționale și structurale
**✅Tests passed: 23 of 23 tests - 48 ms**
---

![image](https://github.com/user-attachments/assets/65da566c-78f1-4eed-ab76-cdf621ea1dec)

**Coverage**
---

![image](https://github.com/user-attachments/assets/0db85e71-b35e-46d0-a894-0eca5aeaa352)


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

![image](https://github.com/user-attachments/assets/b80eec33-83cd-4277-87ef-bc9877f63cc1)

### Testare funcțională - Valori de frontieră (metoda withdraw)
Suma care poate fi retrasă este între 0 și suma disponibilă în cont. Testăm 4 valori de frontieră:
- o valoarea negativă
- 0
- suma disponibilă în cont
- o valoare mai mare decât suma disponibilă în cont
    
![image](https://github.com/user-attachments/assets/a7e4cbce-3de6-499c-9816-bd2d97522179)


### Testare structurală - Acoperire la nivel de instrucțiune
![image](https://github.com/user-attachments/assets/177592ef-9c07-4676-ae52-207e99025a43)

În metoda **exhange** folosită de metoda **deposit** există o instrucțiune de tipul **if - else if**.
Testăm ambele noduri, dând valori adevarăte pentru condiția din if și apoi pentru cea din else if.

![image](https://github.com/user-attachments/assets/97d5c95d-d0df-44bf-ad4e-6aac7a654c09)



### Testare structurală - Acoperire la nivel de ramură

![image](https://github.com/user-attachments/assets/032df79c-0167-4575-8cfc-d3f335721974)

![image](https://github.com/user-attachments/assets/bee82236-a962-452b-ad25-8c89f91a7a3e)

![image](https://github.com/user-attachments/assets/4cd5ece6-5360-4e8a-8a44-95eb29add33e)

#### Testăm fiecare ramură din metoda **deposit**.
---
![image](https://github.com/user-attachments/assets/2d6f19da-6408-4b8b-b024-921b96326be5)

### Testare structurală - Acoperire la nivel de condiție
### Testare structurală - Circuite independente


## Raport Pitest (Mutation testing)
**Primul raport generat**

Mutation Coverage -> 49/52

![image](https://github.com/user-attachments/assets/89b0a454-1cd9-4278-8532-00159c4c2ae3)

**Analiză**

![image](https://github.com/user-attachments/assets/fd5480c6-9751-4127-a846-1284aa8c35ca)

![image](https://github.com/user-attachments/assets/34eb3301-e694-465b-967d-3d8f78e8515f)

**Modificări**

Metoda inițială

![image](https://github.com/user-attachments/assets/c9258663-df98-4074-9e48-59b8fe609a12)

Metoda îmbunătățită

![image](https://github.com/user-attachments/assets/79937602-336f-41e9-bf59-ea80391287d5)

**Al doilea raport generat**

Mutation Coverage -> 52/52

![image](https://github.com/user-attachments/assets/d5c905bf-4f5e-4846-965f-b02abd7aea77)

## Raport AI

Analiză


## Configurare
- **IDE** - Intelij IDEA
- **Java** - 22 &
- **Maven**
- **JUnit** - 5.7.1
- **Pitest** - 1.19.0



