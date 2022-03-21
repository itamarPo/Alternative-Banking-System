package database.loan;

import database.DataBase;

public class Payment {
    private int currentTime;
    private double payComponent;
    private double sumOfPayment;
    private double initialPayment;
    private boolean payedSuccesfully;

    public Payment(int currentTime, double payComponent, double sumOfPayment, double initialPayment, boolean payedSuccesfully) {
        this.currentTime = DataBase.getTime(); //problem with time being public. solved!!!! returned it to private and created a public static getter
        this.payComponent = payComponent;
        this.sumOfPayment = sumOfPayment;
        this.initialPayment = initialPayment;
        this.payedSuccesfully = payedSuccesfully;
    }




}
