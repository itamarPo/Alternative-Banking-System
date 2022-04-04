package objects.loans;

public class NewLoanDTO {
    private String loanID;

    public String getLoanID() {
        return loanID;
    }

    private String borrowerName;

    public double getSizeNoInterest() {
        return sizeNoInterest;
    }

    private String loanCategory;
    private double sizeNoInterest;
    private int timeLimitOfLoan;
    private double interestPerPayment;
    private int timePerPayment;

    public String getStatus() {
        return status;
    }

    private String status;

    public NewLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, double interestPerPayment, int timePerPayment, String status) {
        this.loanID = loanID;
        this.borrowerName = borrowerName;
        this.loanCategory = loanCategory;
        this.sizeNoInterest = sizeNoInterest;
        this.timeLimitOfLoan = timeLimitOfLoan;
        this.interestPerPayment = interestPerPayment;
        this.timePerPayment = timePerPayment;
        this.status = status;
    }

    public void print(){
        System.out.println("Loan ID: " + loanID);
        System.out.println("Owner: " + borrowerName);
        System.out.println("Category: " + loanCategory);
        System.out.println("Loan size: " + sizeNoInterest);
        System.out.println("Time limit: " + timeLimitOfLoan);
        System.out.println("Interest per payment: " + interestPerPayment);
        System.out.println("Time per payment: " + timePerPayment );
        System.out.println("Status: " + status);
    }
}
