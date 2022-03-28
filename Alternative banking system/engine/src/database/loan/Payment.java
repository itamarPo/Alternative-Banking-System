package database.loan;

import database.Engine;

public class Payment {
    private int timeOfPayment;
    private double interestComponent;
    private double sumOfPayment;
    private double initialComponent;
    private boolean payedSuccesfully;

    public Payment(int timeOfPayment, double interestComponent, double sumOfPayment, double initialComponent, boolean payedSuccesfully) {
        this.timeOfPayment = Engine.getTime(); //problem with time being public. solved!!!! returned it to private and created a public static getter
        this.interestComponent = interestComponent;
        this.sumOfPayment = sumOfPayment;
        this.initialComponent = initialComponent;
        this.payedSuccesfully = payedSuccesfully;
    }

    public int getTimeOfPayment() {
        return timeOfPayment;
    }

    public double getInterestComponent() {
        return interestComponent;
    }

    public double getSumOfPayment() {
        return sumOfPayment;
    }

    public double getInitialComponent() {
        return initialComponent;
    }

    public boolean isPayedSuccesfully() {
        return payedSuccesfully;
    }

    public void print(){}
}
