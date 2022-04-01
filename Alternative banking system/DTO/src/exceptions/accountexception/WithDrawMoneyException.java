package exceptions.accountexception;

public class WithDrawMoneyException extends Exception{
    private double balance;
    private double transaction;

    public WithDrawMoneyException(double balance, double transaction) {
        this.balance = balance;
        this.transaction = transaction;
    }

    public void printMessage(){
        System.out.println("This customer cannot Draw " + transaction + " from his account because he only has: " + balance +". Transaction failed.");
    }
}
