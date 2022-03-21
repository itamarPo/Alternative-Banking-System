package database.loan;

import database.DataBase;

public class Payment {
    private int currentTime;
    private double payComponent;
    private double sumOfPayment;
    private double initialPayment;
    private boolean payedSuccesfully;

    public Payment(int currentTime, double payComponent, double sumOfPayment, double initialPayment, boolean payedSuccesfully) {
        this.currentTime = DataBase.time; //problem with time being public.
        this.payComponent = payComponent;
        this.sumOfPayment = sumOfPayment;
        this.initialPayment = initialPayment;
        this.payedSuccesfully = payedSuccesfully;
    }




}
