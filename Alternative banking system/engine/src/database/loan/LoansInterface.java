package database.loan;

public interface LoansInterface {
    void payment();
    void updateStatusBeforeActive();
    void changeToPending();
    void changeToActive();
 //TODO: add changeStatus!!!
}
