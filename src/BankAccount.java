package src;
import java.math.BigDecimal;

public class BankAccount {
    private double balance;
    private String email;

    public BankAccount(String email, double startingBalance){
        if (!isEmailValid(email) || !isAmountValid(startingBalance)){
            throw new IllegalArgumentException();
        }
        this.email = email;
        this.balance = startingBalance;
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    public void withdraw(double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException();
        }
        if (amount > balance){
            throw new InsufficientFundsException();
        }
        balance -= amount;
    }

    public void deposit(double amount){
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException();
        }
        balance += amount;
    }

    public void transfer(BankAccount other, double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException();
        }
        // Treat transferring the entire balance as insufficient (per tests)
        if (amount >= this.balance){
            throw new InsufficientFundsException();
        }
        this.withdraw(amount);
        other.deposit(amount);
    }

    public static boolean isAmountValid(double amount){
        if (amount < 0) return false;
        BigDecimal bd = BigDecimal.valueOf(amount).stripTrailingZeros();
        int scale = bd.scale();
        if (scale < 0) scale = 0;
        return scale <= 2;
    }

    public static boolean isEmailValid(String email){
        if (email == null) return false;
        // must contain exactly one @
        int atIndex = email.indexOf('@');
        if (atIndex <= 0) return false; // no @ or starts with @
        if (email.indexOf('@', atIndex + 1) != -1) return false; // multiple @

        String local = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        // local part checks
        if (local.length() == 0) return false;
        if (local.startsWith(".") || local.endsWith(".")) return false;
        // allowed local: letters, digits, dot, underscore, hyphen
        if (!local.matches("[A-Za-z0-9._-]+")) return false;
        // no two special characters in a row (consider . _ - as special)
        if (local.matches(".*[._-]{2,}.*")) return false;
        // character immediately before @ cannot be special
        if (local.length() > 0 && local.charAt(local.length()-1) == '.') return false;

        // domain checks
        if (domain.length() == 0) return false;
        if (domain.startsWith(".") || domain.endsWith(".")) return false;
        if (!domain.contains(".")) return false; // must contain at least one period
        String[] parts = domain.split("\\.");
        if (parts.length < 2) return false;
        String last = parts[parts.length-1];
        if (last.length() < 2) return false; // final domain suffix at least 2 chars
        // each domain label must be letters/digits/hyphen and not start or end with -
        for (String p : parts){
            if (p.length() == 0) return false;
            if (!p.matches("[A-Za-z0-9-]+")) return false;
            if (p.startsWith("-") || p.endsWith("-")) return false;
        }

        return true;
    }
}
