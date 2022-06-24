//
//package userinterface;
//
//import database.EngineInterface;
//import database.Engine;
//import exceptions.accountexception.notAllAmountSuccessfullyInvested;
//import exceptions.accountexception.NoLoanSelectedForInlay;
//import exceptions.accountexception.NoMatchesFound;
//import exceptions.accountexception.WithDrawMoneyException;
//import exceptions.filesexepctions.*;
//import objects.DisplayCustomerName;
//import objects.categories.CategoriesListDTO;
//import objects.customers.CustomerInfoDTO;
//import objects.loans.*;
//
//import javax.swing.text.StyledEditorKit;
//import javax.xml.bind.JAXBException;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.*;
//
//public class User implements UserInterface {
//    private Engine data;
//    private Scanner scanner;
//    private Boolean FileLoaded;
//
//
//    public User(){
//        this.data = new Engine();
//        this.scanner = new Scanner(System.in);
//        this.FileLoaded = false;
//    }
//
//    public void start(){
//        //boolean start = true;
//       menu();
//    }
//
//    public void menu()  {
//        int userIntegerInput;
//        System.out.println("Welcome!");
//        printMenu();
//        do {
//            userIntegerInput = getValidInput();
//            if(!FileLoaded && userIntegerInput > 1 && userIntegerInput < 8 || !FileLoaded && userIntegerInput > 8) {
//                System.out.println("A file was not loaded! please load a file before choosing any other option.");
//            }
//            else {
//                switch (userIntegerInput) {
//                    case 1: {
//                        //loadFile();
//                        loadFileOption();
//                        break;
//                    }
//                    case 2: {
//                        getLoansInfo();
//                        break;
//                    }
//                    case 3:{
//                        getCustomersInfo();
//                        break;
//                    }
//                    case 4:{
//                        addMoneyToAccount();
//                        break;
//                    }
//                    case 5:{
//                        getMoneyFromAccount();
//                        break;
//                    }
//                    case 6:{
//                        activationOfInlay();
//                        break;
//                    }
//                    case 7:{
//                        moveTimeForward();
//                        break;
//                    }
//                    case 8: {
//                        System.out.println("Goodbye!");
//                        break;
//                    }
//                    case 9: {
//                        bonusPart();
//                    }
//                    default:
//                        break;
//                }
//                if(userIntegerInput != 8){
//                    printMenu();
//                }
//
//            }
//        } while (userIntegerInput != 8) ;
//
//    }
//
//        public int getValidInput() {
//            int userIntegerInput = 0;
//            boolean validInput = false;
//            while (!validInput) {
//                try {
//                    userIntegerInput = scanner.nextInt();
//                    scanner.nextLine(); // clear buffer
//                    validInput = true;
//                }
//                catch (InputMismatchException exception) {
//                    System.out.println("This is not a natural number, please enter a natural number between 1 to 9:");
//                    validInput = false;
//                    scanner.nextLine();
//                }
//                if ((userIntegerInput < 1 || userIntegerInput > 9) && validInput)
//                    System.out.println("Invalid number input! \r\n Please try again. Make sure that you enter a natural number between 1 to 9: ");
//            }
//            return userIntegerInput;
//        }
//
//
//    @Override
//    public void printMenu() {
//        if(Engine.getTime()>1)
//            System.out.print("\r\nPrevious Time: " + (Engine.getTime()-1) + " , ");
//        else
//            System.out.println();
//        System.out.println("Current Time: " + Engine.getTime() + "\r\nPlease select one fo the following options:" );
//        System.out.println("1. Load file ");
//        System.out.println("2. Show loans information and their status  ");
//        System.out.println("3. Show clients information ");
//        System.out.println("4. Add money to an account ");
//        System.out.println("5. Draw money from an account");
//        System.out.println("6. Activation of inlay ");
//        System.out.println("7. Advance To next time period and provide payment");
//        System.out.println("8. Exit program");
//        System.out.println("9. Save current state (bonus).");
//    }
//
//
////    public void loadFile(){
////        Boolean FileLoadedSuccessfully = false;
////        System.out.println("Please enter the file's path that you wish to load. \r\nMake sure the file's type is xml :");
////        try {
////            FileLoadedSuccessfully = data.loadFile(scanner.nextLine() , "Menash");
////        }
////        //JAXB error
////        catch (JAXBException e) {
////            System.out.println("Failed to load file, reason: ");
////            System.out.println("JAXB related error");
////        } catch (FileNotFoundException e) {
////            System.out.println("Failed to load file, reason: ");
////            System.out.println("The file's path was incorrect. please make sure that the path is correct.");
////        } catch (TwoClientsWithSameNameException e) {
////            System.out.println("Failed to load file, reason: ");
////            e.printMessage();
////        } catch (NotXmlExcpetion e) {
////            System.out.println("Failed to load file, reason: ");
////            e.printMessage();
////        } catch (OwnerLoanNotExistException e) {
////            System.out.println("Failed to load file, reason: ");
////            e.printMessage();
////        } catch (LoanCategoryNotExistException e){
////            System.out.println("Failed to load file, reason: ");
////            e.printMessage();
////        } catch (TimeOfPaymentNotDivideEqualyException e){
////            System.out.println("Failed to load file, reason: ");
////            e.printMessage();
////        } catch (Exception e) {
////            System.out.println("Failed to load file, reason: ");
////            e.printStackTrace();
////        }
////        if(FileLoadedSuccessfully){
////            FileLoaded = true;
////            System.out.println("File loaded successfully!");
////        }
////
////
////    }
//
//    @Override
//    public void getLoansInfo() {
//        List<NewLoanDTO> loans = data.getLoansInfo();
//        for(NewLoanDTO loan : loans){
//            loan.print();
//            System.out.println();
//        }
//    }
//
//    @Override
//    public void getCustomersInfo() {
//        List<CustomerInfoDTO> customersInfo = data.getCustomersInfo();
//        for(CustomerInfoDTO customerInfo : customersInfo)
//            customerInfo.print();
//    }
//
//
//    @Override
//    public void moveTimeForward() {
//       // data.moveTimeForward();
//    }
//
//
//
//    @Override
//    public void addMoneyToAccount() {
//        System.out.println("Please select the number of the desired customer:");
//        DisplayCustomerName customersList = data.namesForDisplay();
//        customersList.printNamesAndBalance();
//        int numOfCustomers = customersList.getCustomerList().size();
//        int userChoice = validUserCustomerChoice(numOfCustomers);
//        double moneyToAdd = -1;
//        System.out.println("Please enter the amount you wish to add. make sure that you enter a positive number:");
//        moneyToAdd = validTransactionChoice();
//        //data.addMoneyToAccount(userChoice,moneyToAdd);
//        System.out.println("The money was successfully added. ");
//    }
//    @Override
//    public void getMoneyFromAccount() {
//        System.out.println("Please select the number of the desired customer:");
//        DisplayCustomerName customersList = data.namesForDisplay();
//        customersList.printNamesAndBalance();
//        int numOfCustomers = customersList.getCustomerList().size();
//        int userChoice = validUserCustomerChoice(numOfCustomers);
//        double moneyToDraw = -1;
//        System.out.println("Please enter the amount you wish to draw. make sure that you enter a positive number and not more than the customer has: ");
//        moneyToDraw = validTransactionChoice();
//      //  try {
//           // data.drawMoneyFromAccount(userChoice,moneyToDraw);
//            System.out.println("The money was successfully withdrawn. ");
//      //  }
//       // catch (WithDrawMoneyException e){
//          //  e.printMessage();
//       // } catch (Exception e) {
//        //    e.printStackTrace();
//       // }
//    }
//
//    @Override
//    public void activationOfInlay() {
//        DisplayCustomerName customerNames = data.namesForDisplay();
//        int numOfCustomers = customerNames.getCustomerList().size();
//        System.out.println("Please select the number of the desired customer for the inlay:");
//        customerNames.printNamesAndBalance();
//        int userChoice = validUserCustomerChoice(numOfCustomers);
//
//        try {
//            System.out.println("Please enter a positive number for the customer to invest: (This option is mandatory!)");
//            double moneyToInvest = validTransactionChoice();
//            String customerSelected = null;
//           // data.checkAmountOfInvestment(userChoice, moneyToInvest);
//            int i = 1,j = 0;
//            for (Map.Entry<String,Double> entry : customerNames.getCustomerList().entrySet()){
//                if(j == userChoice - 1){
//                    customerSelected = entry.getKey();
//                }
//                j++;
//            }
//            List<NewLoanDTO> possibleLoans = getInlayDetails(customerSelected);
//            if(possibleLoans.size() == 0)
//                throw new NoMatchesFound();
//            for(NewLoanDTO loan : possibleLoans){
//                System.out.println(i + ":");
//                loan.print();
//                i++;
//            }
//            System.out.println("\r\nPlease enter the loan numbers that you wish to invest: \r\n(The loan's details are above this statement. Please enter the numbers seperated by spaces or ENTER if you're not interested to invest in any of them.\r\nPlease note that entering the same number twice will make no difference.)");
//            possibleLoans = filterLoansAccordingToUser(possibleLoans);
////            data.splitMoneyBetweenLoans(possibleLoans, (int)moneyToInvest, customerSelected, 50);
//            System.out.println("The inlay was completed successfully!");
//        }
//        catch(WithDrawMoneyException e){
//            e.printInvestMessage();
//            return;
//        }catch(NoLoanSelectedForInlay e){
//            e.print();
//        } catch(NoMatchesFound e){
//            e.printMessage();
//        }catch (notAllAmountSuccessfullyInvested e) {
//            e.print();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<NewLoanDTO> filterLoansAccordingToUser(List<NewLoanDTO> possibleLoans) throws Exception{
//        boolean validInput = false;
//        List<NewLoanDTO> LoansSelected = new ArrayList<>();
//        while (!validInput) {
//            String input = scanner.nextLine();
//
//            if (input.equals("")) {
//                throw new NoLoanSelectedForInlay();
//            }
//            String[] optionalNumbers = input.split(" ");
//            for (String possibleNumber : optionalNumbers) {
//                validInput = true;
//                try {
//                    int number = Integer.parseInt(possibleNumber);
//                    if (number > possibleLoans.size() || number < 1) {
//
//                        throw new NumberFormatException();
//                    }
//                    if(!LoansSelected.contains(possibleLoans.get(number - 1))){
//                        LoansSelected.add(possibleLoans.get(number - 1));
//                    }
//
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input! please make sure to enter loans numbers seperated by spaces or ENTER. \r\nPlease try again: ");
//                    validInput = false;
//                    LoansSelected.clear();
//                    break;
//                }
//            }
//        }
//        return LoansSelected;
//    }
//
//    public List<NewLoanDTO> getInlayDetails(String userName) throws Exception{
//        System.out.println("Please select the desired categories from the list: \r\n(Enter categories numbers seperated by spaces. This option isn't mandatory! if you're not interested just press ENTER (no categories to filter).\r\nPlease note that entering the same number twice will make no difference)");
//        CategoriesListDTO systemCategories = data.getCategoriesList();
//        systemCategories.print();
//        List<String> categoriesAfterFilter = new ArrayList<>();
//        categoriesAfterFilter = getFilteredCategories(categoriesAfterFilter,systemCategories.getCategoriesList());
//        System.out.println("Please select the minimum interest you're willing to accept. Make sure you enter an Integer number between 1 to 99: \r\n(This option isn't mandatory! If you aren't interested in this option please press ENTER)");
//        int interest = getPositiveIntToInterest();
//        System.out.println("Please select the minimum time of loan you're willing to accept. Make sure you enter an Integer number: \r\n(This option isn't mandatory! If you aren't interested in this option please press ENTER)");
//        int minTime = getPositiveInt();
//        return data.getFilteredLoans(categoriesAfterFilter, interest,minTime,userName, 2);
//    }
//
//    public List<String> getFilteredCategories(List<String> categoriesAfterFilter, List<String> categoriesBeforeFilter) {
//        boolean validInput = false;
//        while (!validInput) {
//            String input = scanner.nextLine();
//            if (input.equals("")) {
//                categoriesAfterFilter = categoriesBeforeFilter;
//                validInput = true;
//            }
//            else {
//                String[] choices = input.split(" ");
//                for (String choice : choices) {
//                    validInput = true;
//                    try {
//                        int number = Integer.parseInt(choice);
//                        if (number > categoriesBeforeFilter.size() || number < 1) {
//                            throw new NumberFormatException();
//                        }
//                        if(!categoriesAfterFilter.contains(categoriesBeforeFilter.get(number-1))) {
//                            categoriesAfterFilter.add(categoriesBeforeFilter.get(number - 1));
//                        }
//                    } catch (NumberFormatException e) {
//                        System.out.println("Invalid input! please make sure you enter the categories numbers seperated by spaces or ENTER. \r\nPlease try again: ");
//                        categoriesAfterFilter.clear();
//                        validInput = false;
//                        break;
//                    }
//                }
//            }
//        }
//        return categoriesAfterFilter;
//    }
//
//    public int getPositiveInt(){
//        boolean validInput = false;
//        int number = 0;
//        while (!validInput) {
//            String input = scanner.nextLine();
//            if (input.equals("")) {
//                return number;
//            } else {
//                validInput = true;
//                try {
//                    number = Integer.parseInt(input);
//                    if(number < 1) {
//                        throw new NumberFormatException();
//                    }
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input! Please make sure you enter a valid number. \r\nPlease try again:");
//                    validInput = false;
//                }
//            }
//        }
//        return number;
//    }
//    public int getPositiveIntToInterest(){
//        boolean validInput = false;
//        int number = 0;
//        while (!validInput) {
//            String input = scanner.nextLine();
//            if (input.equals("")) {
//                return number;
//            } else {
//                validInput = true;
//                try {
//                    number = Integer.parseInt(input);//Double.parseDouble(input);
//                    if(number < 0 || number > 100) {
//                        throw new NumberFormatException();
//                    }
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input! Please make sure you enter an Integer between 1 to 99. \r\nPlease try again:");
//                    validInput = false;
//                }
//            }
//        }
//        return number;
//    }
//    public int validUserCustomerChoice(int numOfCustomers) {
//        int userChoice = 0;
//        do{
//            try {
//                userChoice = scanner.nextInt();
//                if (userChoice < 1 || userChoice > numOfCustomers) {
//                    System.out.println("Invalid input. Please enter a natural number between: 1" + " to " + numOfCustomers );
//                }
//            } catch (InputMismatchException exception) {
//                System.out.println("This isn't a number! Please enter a natural number between: 1" + " to " + numOfCustomers);
//            }
//            scanner.nextLine(); //Buffer
//        } while (userChoice < 1 || userChoice > numOfCustomers);
//        return userChoice;
//    }
//    public double validTransactionChoice(){
//        double moneyToDraw = 0;
//        do {
//            try {
//                moneyToDraw = scanner.nextDouble();
//                scanner.nextLine(); //buffer
//                if (moneyToDraw <= 0)
//                    System.out.println("Invalid input. Please enter a positive number!");
//            } catch (InputMismatchException exception) {
//                System.out.println("Incorrect Input. Please make sure that you enter a positive number.");
//                scanner.nextLine();
//            }
//        } while (moneyToDraw <= 0);
//        return moneyToDraw;
//    }
//
//    public void bonusPart (){
//        System.out.println("Please enter the full path's directory (including the file's name), that you wish to save into. \r\nNote: no need to save the file's type. " +
//                "we automatically convert it to the desired type. \r\nfor example:\r\n C:\\Users\\itamarpo\\IdeaProjects\\Alternative-Banking-System\\Alternative banking system\\engine\\src\\database\\fileresource\\itamarTest\\FILE\r\nWould be converted to: \r\n" +
//                "C:\\Users\\itamarpo\\IdeaProjects\\Alternative-Banking-System\\Alternative banking system\\engine\\src\\database\\fileresource\\itamarTest\\FILE.xtxt \r\n Enter your path below: ");
//        String filePath = scanner.nextLine();
//        //scanner.nextLine(); //buffer
//        try {
//            data.saveState(filePath, data);
//            System.out.println("A file was Successfully saved.");
//        }
//        catch (IOException e){
//            System.out.println("IOException error");
//        }
//    }
//
//    public void loadFileOption(){
//        System.out.println("Please choose between the next options: \r\n1. Load new File: \r\n2. Load previously saved Files: ");
//        int userInput = getPositiveInt();;
//
//        while(userInput>2){
//            System.out.println("Invalid Input, Please Choose 1 or 2.");
//            userInput = getPositiveInt();
//        }
//        if(userInput == 1)
//            //loadFile();
//        else
//            loadBonus();
//    }
//
//    public void loadBonus() {
//        System.out.println("Please enter the file's path that you wish to load, EXCLUDING its type!!!! (we automatically convert it to the desired type): \r\n" +
//                "for example:\r\nC:\\Users\\itamarpo\\IdeaProjects\\Alternative-Banking-System\\Alternative banking system\\engine\\src\\database\\fileresource\\itamarTest\\FILE \r\nWould be converted to: \r\n" +
//                "C:\\Users\\itamarpo\\IdeaProjects\\Alternative-Banking-System\\Alternative banking system\\engine\\src\\database\\fileresource\\itamarTest\\FILE.xtxt \r\n" +
//                "Enter below your path: " );
//        String filePath = scanner.nextLine();
//        System.out.println();
//        try {
//            data = data.loadLastFile(filePath);
//            System.out.println("A file was successfully loaded.");
//            FileLoaded = true;
//        }
//        catch (Exception e) {
//            System.out.println("The file was unsuccessfully loaded! \r\n Please make sure that the file belongs to a previously saved state.");
//        }
//    }
//
//}
//
