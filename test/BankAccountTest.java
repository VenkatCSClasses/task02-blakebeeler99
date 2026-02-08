package test;
import org.junit.Test;

import src.BankAccount;
import src.InsufficientFundsException;

import static org.junit.Assert.*;

public class BankAccountTest {

    @Test
    public void getBalance_integerStartingBalance(){
        BankAccount b = new BankAccount("a@b.com", 200.00);
        assertEquals(200.00, b.getBalance(), 0.0001);
    }

    @Test
    public void getBalance_decimalStartingBalance(){
        BankAccount b = new BankAccount("a@b.com", 120.34);
        assertEquals(120.34, b.getBalance(), 0.0001);
    }

    @Test
    public void withdraw_valid(){
        BankAccount b = new BankAccount("a@b.com", 200.00);
        try{ b.withdraw(100.00); } catch(Exception e){ fail("unexpected"); }
        assertEquals(100.00, b.getBalance(), 0.0001);
    }

    @Test
    public void withdraw_zero(){
        BankAccount b = new BankAccount("a@b.com", 200.00);
        try{ b.withdraw(0.00); } catch(Exception e){ fail("unexpected"); }
        assertEquals(200.00, b.getBalance(), 0.0001);
    }

    @Test
    public void withdraw_negative_throwsIllegalArgument(){
        BankAccount b = new BankAccount("a@b.com", 200.00);
        try{ b.withdraw(-50.00); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){ }
        catch(Exception e){ fail("Wrong exception"); }
    }

    @Test
    public void withdraw_insufficientFunds_throwsInsufficient(){
        BankAccount b = new BankAccount("a@b.com", 200.00);
        try{ b.withdraw(300.00); fail("Expected InsufficientFundsException"); }
        catch(InsufficientFundsException e){ }
        catch(Exception e){ fail("Wrong exception"); }
    }

    @Test
    public void withdraw_borderNegative_throwsIllegalArgument(){
        BankAccount b = new BankAccount("a@b.com", 200.00);
        try{ b.withdraw(-0.01); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){ }
        catch(Exception e){ fail("Wrong exception"); }
    }

    @Test
    public void withdraw_threeDecimal_throwsIllegalArgument(){
        BankAccount b = new BankAccount("a@b.com", 200.00);
        try{ b.withdraw(0.011); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){ }
        catch(Exception e){ fail("Wrong exception"); }
    }

    @Test
    public void isEmailValid_examples(){
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertFalse(BankAccount.isEmailValid(""));
        assertFalse(BankAccount.isEmailValid("abc..@gmail.com"));
        assertFalse(BankAccount.isEmailValid("abc.@gmail..com"));
        assertTrue(BankAccount.isEmailValid("abc.a@gmail.com"));
        assertFalse(BankAccount.isEmailValid("abcgmail.com"));
        assertTrue(BankAccount.isEmailValid("abc@gmail.com"));
        assertFalse(BankAccount.isEmailValid("@gmail.com"));
        assertTrue(BankAccount.isEmailValid("a@gmail.com"));
        assertFalse(BankAccount.isEmailValid("abc#@gmail.com"));
        assertTrue(BankAccount.isEmailValid("abc@gmail.com"));
        assertFalse(BankAccount.isEmailValid(".abc@gmail.com"));
        assertFalse(BankAccount.isEmailValid("abc.@gmail.com"));
        assertFalse(BankAccount.isEmailValid("abc@"));
        assertTrue(BankAccount.isEmailValid("abc@b.com"));
        assertFalse(BankAccount.isEmailValid("abc@com"));
        assertFalse(BankAccount.isEmailValid("abc@.com"));
        assertFalse(BankAccount.isEmailValid("abc@b.c"));
        assertTrue(BankAccount.isEmailValid("abc@b.cc"));
        assertFalse(BankAccount.isEmailValid("abc@b#.com"));
        assertTrue(BankAccount.isEmailValid("abc@b-b.com"));
    }

    @Test
    public void constructor_validAndInvalid(){
        BankAccount b = new BankAccount("a@b.com", 200.00);
        assertEquals("a@b.com", b.getEmail());
        assertEquals(200.00, b.getBalance(), 0.0001);

        try{ new BankAccount("", 100.00); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}

        try{ new BankAccount("a@b.com", -100.00); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}

        try{ new BankAccount("a@b.com", 100.999); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}
    }

    @Test
    public void isAmountValid_checks(){
        assertTrue(BankAccount.isAmountValid(100.00));
        assertTrue(BankAccount.isAmountValid(0.00));
        assertFalse(BankAccount.isAmountValid(-0.01));
        assertFalse(BankAccount.isAmountValid(-100.00));
        assertFalse(BankAccount.isAmountValid(100.10000909));
        assertFalse(BankAccount.isAmountValid(100.999));
        assertTrue(BankAccount.isAmountValid(100.00));
        assertTrue(BankAccount.isAmountValid(100.9));
    }

    @Test
    public void deposit_validAndInvalid(){
        BankAccount b = new BankAccount("a@b.com", 100.00);
        b.deposit(50.00);
        b.deposit(0.00);
        assertEquals(150.00, b.getBalance(), 0.0001);

        try{ b.deposit(-20.00); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}

        try{ b.deposit(-0.01); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}

        try{ b.deposit(0.011); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}
    }

    @Test
    public void transfer_valid_and_error_cases() {
        BankAccount a = new BankAccount("a@b.com", 100.00);
        BankAccount b = new BankAccount("a@b.com", 200.00);
        try{ a.transfer(b, 50.00); } catch(Exception e){ fail("unexpected"); }
        try{ a.transfer(b, 0.00); } catch(Exception e){ fail("unexpected"); }
        assertEquals(50.00, a.getBalance(), 0.0001);
        assertEquals(250.00, b.getBalance(), 0.0001);

        // error cases - use fresh accounts
        BankAccount c1 = new BankAccount("a@b.com", 100.00);
        BankAccount c2 = new BankAccount("a@b.com", 200.00);
        try{ c1.transfer(c2, -30.00); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}
        catch(InsufficientFundsException e){ fail("Wrong exception"); }

        try{ c1.transfer(c2, -0.01); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}
        catch(InsufficientFundsException e){ fail("Wrong exception"); }

        try{ c1.transfer(c2, 0.011); fail("Expected IllegalArgumentException"); }
        catch(IllegalArgumentException e){}
        catch(InsufficientFundsException e){ fail("Wrong exception"); }

        try{ c1.transfer(c2, 100.00); fail("Expected InsufficientFundsException"); }
        catch(InsufficientFundsException e){}
        catch(Exception e){ fail("Wrong exception"); }
    }
}
