package objects.customers;

public class PaymentNotificationDTO {
    //Class Fields
    private String loanID;
    private int paymentYaz;
    private int sumOfPayment;

    //Constructor
    public PaymentNotificationDTO(String loanID, int paymentYaz, int sumOfPayment) {
        this.loanID = loanID;
        this.paymentYaz = paymentYaz;
        this.sumOfPayment = sumOfPayment;
    }

    //Getters
    public String getLoanID() {return loanID;}
    public int getPaymentYaz() {return paymentYaz;}
    public int getSumOfPayment() {return sumOfPayment;}
}
