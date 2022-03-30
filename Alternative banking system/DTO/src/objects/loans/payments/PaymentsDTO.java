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
    }

    public void print(){
        System.out.println("Time of payment: " + timeOfPayment);
        System.out.println("Interest component: " + interestComponent);
        System.out.println("Initial component: " + initialComponent);
        System.out.println("Total sum of payment: " + sumOfPayment);
        if(payedSuccesfully)
            System.out.println("Payment status: payed");
        else
            System.out.println("Payment status: not payed");

    }
}
