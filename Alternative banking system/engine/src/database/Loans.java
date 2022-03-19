package database;

import java.util.List;

public interface Loans {
    int getID();
    int getStartTimeofLoan();
    int getEndTimeofLoan();
    String getBorrowerName();
    String getLoanCategory();
    List<String> getLendersList();

}
