# E-Wallet System

Java console application implementing an e-wallet flow.

## Features
- Signup validation
- Unique username and Egyptian phone
- Password complexity validation
- Age >= 18
- Login with 3 attempts
- Deposit / Withdraw / Transfer
- Account details
- Change password
- Logout
- Transaction history
- Admin panel
- Delete account
- Activate / deactivate account
- Exception-safe input handling

## Default Admin
- Username: IAM
- Password: IAM123

The default admin is automatically created when the application starts.

## Username Rules
- 3 to 20 characters
- First character must be uppercase
- Letters, numbers and `_` are allowed

## Password Rules
- At least 8 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one digit

## Egyptian Phone
Example: 01012345678

## Run
From the EWalletSystem directory:

```bash
javac src/*.java
java -cp src EWalletSystem
```
