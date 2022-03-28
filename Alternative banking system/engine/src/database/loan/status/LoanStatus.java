package database.loan.status;

import com.sun.xml.internal.bind.v2.TODO;
import database.Engine;
import database.loan.Payment;

import java.util.ArrayList;
import java.util.List;

public class LoanStatus implements LoanStatusInterface {
 //TODO: changes to status according to the word file.
    NEW("New", null, 0, 0,0,0,0,0,0),
    PENDING("Pending", null,0,0,0,0,0,0,0),
    ACTIVE("Active", null, 0,0,0,0,0,0,0),
    RISK("Risk",null,0,0,0,0,0,0,0),
    FINISHED("Finished",null,0,0,0,0,0,0,0);

    private String status;
    private List<Payment> payments;
    private int startingActiveTime;
    private int finishTime;
    private int nextPaymentTime;
    private double interestPayed;
    private double initialPayed;

    public String getStatus() { return status;}

    private double interestLeftToPay;
    private double initialLeftToPay;

    public List<Payment> getPayments() {
        return payments;
    }

    public int getStartingActiveTime() {
        return startingActiveTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    LoanStatus(String status, List<Payment> payments, int startingActiveTime, int finishTime, int nextPaymentTime, double interestPayed, double initialPayed, double interestLeftToPay, double initialLeftToPay) {
        this.status = status;
        this.payments = payments;
        this.startingActiveTime = startingActiveTime;
        this.finishTime = finishTime;
        this.nextPaymentTime = nextPaymentTime;
        this.interestPayed = interestPayed;
        this.initialPayed = initialPayed;
        this.interestLeftToPay = interestLeftToPay;
        this.initialLeftToPay = initialLeftToPay;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addPay(Payment payment) {
        this.payments.add(payment) ;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public LoanStatus() {
        this.status = "Active";
        this.payments = new ArrayList<>();
        this.startingActiveTime = Engine.getTime();
        this.finishTime = 0;
        this.nextPaymentTime = startingActiveTime;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public void print() {

    }
}
