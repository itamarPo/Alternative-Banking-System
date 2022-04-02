package exceptions.accountexception;

public class LoansDoesNotReachSumOfInvestment extends Exception{
    private double moneyToInvest;
    private double sumOfLoans;

    public LoansDoesNotReachSumOfInvestment(double moneyToInvest, double sumOfLoans) {
        this.moneyToInvest = moneyToInvest;
        this.sumOfLoans = sumOfLoans;
    }
    public void print(){
        System.out.println("The sum of all the loans you have chosen is only " + sumOfLoans + " while the amount you \r\nwanted to invest is " + moneyToInvest + " Inlay denied!");
    }

}
