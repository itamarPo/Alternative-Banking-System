package objects.Loans;

public class NewLoanDTO {
    private String loanID;
    private String borrowerName;
    private String loanCategory;
    private double sizeNoInterest;
    private int timeLimitOfLoan;
    private int interestPerPayment;
    private int timePerPayment;
    private String status;

    public NewLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, int interestPerPayment, int timePerPayment, String status) {
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

    }

}
