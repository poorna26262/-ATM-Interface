# ATM Interface – Java Console Project

## Requirements
- JDK 8 or later
- IntelliJ IDEA, Eclipse, VS Code, or terminal

## Compile and run
Open a terminal in the `src` folder:

```bash
javac *.java
java Main
```

## Demo accounts
| User ID | PIN | Opening balance |
|---|---|---:|
| U1001 | 1234 | ₹5,000.00 |
| U1002 | 4321 | ₹3,000.00 |
| U1003 | 1111 | ₹7,500.00 |

## Features
- User ID and PIN authentication, maximum 3 attempts
- Transaction history stored in an `ArrayList` for the current program session
- Withdraw and deposit with amount validation
- Transfer between demo accounts
- Sufficient-balance check for withdrawals and transfers
- Both accounts' balances and transaction histories are updated for transfers
- Menu implemented with `switch`
- Five required classes: Main, ATM, Account, Transaction, Bank

## Important
This is an educational in-memory simulation. Balances, accounts, and history reset when the program closes. It is not connected to a real bank. Do not use real account details or PINs. A production system needs secure PIN hashing, persistent storage, audit logging, and proper transaction handling.
