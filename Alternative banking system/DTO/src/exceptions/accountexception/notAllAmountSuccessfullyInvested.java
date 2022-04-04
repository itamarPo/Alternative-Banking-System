package exceptions.accountexception;

public class notAllAmountSuccessfullyInvested extends Exception{
    private double returnToAccount;
    private double actuallyInvested;

    public notAllAmountSuccessfullyInvested(double returnToAccount, double sumOfLoans) {
        this.returnToAccount = returnToAccount;
        this.actuallyInvested = sumOfLoans;
    }
    public void print(){
        System.out.println("A total sum of " + actuallyInvested +" has successfully invested. The other "+returnToAccount + " has been returned to your account.");
    }

}
