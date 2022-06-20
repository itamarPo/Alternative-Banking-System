package objects.admin;

import objects.customers.CustomerInfoDTO;
import objects.loans.NewLoanDTO;

import java.util.List;

public class LoanAndCustomerInfoDTO {
    List<CustomerInfoDTO> customerList;
    List<NewLoanDTO> loanList;

    public LoanAndCustomerInfoDTO(List<CustomerInfoDTO> customerList, List<NewLoanDTO> loanList) {
        this.customerList = customerList;
        this.loanList = loanList;
    }

    public List<CustomerInfoDTO> getCustomerList() {
        return customerList;
    }

    public List<NewLoanDTO> getLoanList() {
        return loanList;
    }
}
