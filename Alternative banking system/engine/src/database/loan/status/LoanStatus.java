package database.loan.status;

import com.sun.xml.internal.bind.v2.TODO;
import database.Engine;
import database.loan.Payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoanStatus implements LoanStatusInterface, Serializable {


    private String status;
    private List<Payment> payments;
    private int startingActiveTime;
    private int finishTime;
    private int nextPaymentTime;
    private double interestPayed;
    private double initialPayed;
    private double interestLeftToPay;
    private double initialLeftToPay;

    public LoanStatus(String status, int startingActiveTime, int finishTime, int nextPaymentTime, double interestPayed, double initialPayed, double interestLeftToPay, double initialLeftToPay) {
        this.status = status;
        this.payments = new ArrayList<>();
        this.startingActiveTime = startingActiveTime;
        this.finishTime = finishTime;
        this.nextPaymentTime = nextPaymentTime;
        this.interestPayed = interestPayed;
        this.initialPayed = initialPayed;
        this.interestLeftToPay = interestLeftToPay;
        this.initialLeftToPay = initialLeftToPay;
    }

    public void setInterestPayed(double interestPayed) {
        this.interestPayed += interestPayed;
    }

    public void setInitialPayed(double initialPayed) {
        this.initialPayed += initialPayed;
    }

    public void setInterestLeftToPay(double interestLeftToPay) {
        this.interestLeftToPay -= interestLeftToPay;
    }

    public void setInitialLeftToPay(double initialLeftToPay) {
        this.initialLeftToPay -= initialLeftToPay;
    }

    public String getStatus() { return status;}
    public List<Payment> getPayments() {
        return payments;
    }

    public List<Payment> getSupposedPayments() {
        return supposedPayments;
    }
    public int getStartingActiveTime() {
        return startingActiveTime;
    }
    public int getFinishTime() {
        return finishTime;
    }
    public int getNextPaymentTime() {
        return nextPaymentTime;
    }
    public double getInterestPayed() {
        return interestPayed;
    }
    public double getInitialPayed() {
        return initialPayed;
    }
    public double getInterestLeftToPay() {
        return interestLeftToPay;
    }
    public double getInitialLeftToPay() {return initialLeftToPay;}
    public void addPayment(Payment payment) {
        this.payments.add(payment) ;
    }
    //setters
    public void setStatus(String status) {
        this.status = status;
    }
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
    public void setStartingActiveTime(int startingActiveTime) {
        this.startingActiveTime = startingActiveTime;
    }

    public void setNextPaymentTime(int nextPaymentTime) {
        this.nextPaymentTime += nextPaymentTime;
    }
    public Payment returnLastPayment(){return payments.get(payments.size()-1);}
}
