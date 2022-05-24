package objects.loans.payments;

public class PaymentsDTO {
    private int timeOfPayment;
    private double interestComponent;
    private double sumOfPayment;
    private double initialComponent;
    private boolean payedSuccesfully;

    public PaymentsDTO(int timeOfPayment, double interestComponent, double sumOfPayment, double initialComponent, boolean payedSuccesfully) {
        this.timeOfPayment = timeOfPayment;
        this.interestComponent = interestComponent;
        this.sumOfPayment = sumOfPayment;
        this.initialComponent = initialComponent;
        this.payedSuccesfully = payedSuccesfully;
        if(this.payedSuccesfully)
            payedSuccessfully = "Payed";
        else
            payedSuccessfully = "Not Payed";
    }

    public void print(){
        System.out.println("Time of payment: " + timeOfPayment);
        System.out.println("Interest component: " + String.format("%.2f", interestComponent));
        System.out.println("Initial component: " + String.format("%.2f", initialComponent));
        System.out.println("Total sum of payment: " + String.format("%.2f", sumOfPayment));
//        if(payedSuccesfully)
//            System.out.println("Payment status: payed");
//        else
//            System.out.println("Payment status: not payed");

    }
}
