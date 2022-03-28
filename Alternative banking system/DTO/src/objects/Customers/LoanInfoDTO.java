package objects.Customers;

public class LoanInfoDTO implements LoanInfoDTOInterface{

    private double loanSize;
    private String loanName;
    private String loanCategory;
    private double sizeNoInterest;
    private int interestPerPayment;
    private int timePerPayment;
    private String status;

    public LoanInfoDTO(double loanSize, String loanName, String loanCategory, double sizeNoInterest, int interestPerPayment, int timePerPayment, String status) {
        this.loanSize = sizeNoInterest + sizeNoInterest*interestPerPayment/100;
        this.loanName = loanName;
        this.loanCategory = loanCategory;
        this.sizeNoInterest = sizeNoInterest;
        this.interestPerPayment = interestPerPayment;
        this.timePerPayment = timePerPayment;
        this.status = status;
    }
    public double getLoanSize() {
        return loanSize;
    }

    public String getLoanName() {
        return loanName;
    }

    public String getLoanCategory() {
        return loanCategory;
    }

    public double getSizeNoInterest() {
        return sizeNoInterest;
    }

    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    public int getTimePerPayment() {
        return timePerPayment;
    }

    public String getStatus() {
        return status;
    }

    public void print(){
        System.out.println("\r\nLoan name: " + loanName);
        System.out.println("Loan size without interest: " + sizeNoInterest);
        System.out.println("Loan size with interest: " + loanSize);
        System.out.println("Category: " + loanCategory);
        System.out.println("Interest per payment: " + interestPerPayment);
        System.out.println("Time per payment: " + timePerPayment );
        System.out.println("Status: " + status);
    }
}
