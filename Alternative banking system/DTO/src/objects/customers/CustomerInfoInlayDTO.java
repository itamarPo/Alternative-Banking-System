package objects.customers;

public class CustomerInfoInlayDTO {
    private boolean withDrawException;
    private String result;
    private int openLoans;

    public CustomerInfoInlayDTO(boolean withDrawException, String result, int openLoans) {
        this.withDrawException = withDrawException;
        this.result = result;
        this.openLoans = openLoans;
    }

    public boolean isWithDrawException() {
        return withDrawException;
    }

    public String getResult() {
        return result;
    }

    public int getOpenLoans() {
        return openLoans;
    }
}
