
package database;

import database.client.ClientImp;
import database.loan.LoansImpl;

import java.util.*;

public class DataBase {
   private List<ClientImp> clients;
   private List<LoansImpl> loans;
   private Map<String, List<LoansImpl>> loansByCategories;
   private static int time=0;

   public DataBase(){
      clients = new ArrayList<ClientImp>();
      loans = new ArrayList<LoansImpl>();
      loansByCategories = new Hashtable<>();
   }

   public static int getTime(){return time;}
   public void loadFile(){
      //... zarih lirot ma ha peulot she anu osim kshe anahnu toanim at ha kovetz
      //loansBycategorizes.add(...)
      //loans.add(...)
      //clients.add(...)
      //etc

   }

}
