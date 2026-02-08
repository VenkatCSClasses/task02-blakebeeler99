# BankAccount Specification

## Overview

A Bank account class with an amount and an email. It can be accessed, deposited into, withdrawn from, and money can be transferred between bank accounts.

## Output Structure

## Output Structure

Generate the minimal files needed to use and test the library. Do not create package distribution scaffolding.

**Do generate:**
- Library source file(s)
- Test file(s)
- readme.md

## Functions

### Constructor

Takes email and starting balance as arguments. Checks if both are valid using the is email valid and is amount valid methods. If so, creates a bank account object with the email as the email attribute and the starting balance as its balance attribute.

**Attributes**
- startingbalance: a money amount to open the bank account with
- email: a valid email to be assigned to the bank account

### getBalance
Returns the current balance of the bank account.


### getEmail
Returns the email attribute associated with the bank account.



### withdraw
Reduces the account balance by amount when valid.

Behavior:
- Checks if amount is valid using isAmountValid
- if it is invalid, throws an error
- if it is valid, checks if the amount is <= the current balance of the account
- if it is not, throws an error
- if it is, that amount is subtracted from the current balance

**Errors**
- if the amount is invalid
- if the amount is > the balance of the account


### deposit
Increases the account balance by amount when valid.


Behavior:
- checks if amount is valid using isAmountValid
- if it is the ammount is added to the balance
- if it is not, an error is thrown

**Errors**
- if the amount is invalid



### transfer
Transfers money from this account to another bankAccount

Behavior:
- Validates amount using isAmountValid
- Attempts to withdraw money from the first account
- If throws an exception, this function also throws an exceptiom
- If the withdraw is successful the amount is deposited into the other account.

**Errors**
- if the amount is invalid
- if the withraw throws an error


### isEmailValid
Static validator that returns a boolean if the email is valid or not according to the rules.

Rules:
- Must contain a single `@` symbol.
- The prefix (before `@`) cannot be empty and cannot start or end with a period.
- Two special characters cannot appear consecutively in the local part (prefix).
- The character immediately before `@` cannot be a special character.
- The domain (after `@`) must contain at least one period (`.`), and the final domain suffix must be at least two characters long.
- The domain cannot start with a period and cannot end with a period.
- Allowed characters include letters, digits, and common email special characters; several invalid examples are tested.

Returns:
- true if the email satisfies the checks, false otherwise.

Examples:
- `a@b.com` → `true`
- `""` (empty string) → `false`
- `abc..@gmail.com` → `false`
- `abc@com` → `false`
- `abc@b.cc` → `true`


### isAmountValid
Static validator for monetary amounts.

Rules:
- Amount must be non-negative 
- Amount must have at most two decimal places.

Returns:
- true if the amount is valid, false otherwise.

Examples:
- `isAmountValid(100.00)` → `true`
- `isAmountValid(0.00)` → `true` (border)
- `isAmountValid(-0.01)` → `false` (negative border)
- `isAmountValid(100.999)` → `false` (more than two decimals)
- `isAmountValid(100.9)` → `true`