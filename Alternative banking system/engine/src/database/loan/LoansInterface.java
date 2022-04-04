package database.loan;

public interface LoansInterface extends Comparable<Loans>{
    void payment();
    void updateStatusBeforeActive();
    void changeToPending();
    void changeToActive();

    int compareTo(Loans loan);
    //TODO: add changeStatus!!!
}
