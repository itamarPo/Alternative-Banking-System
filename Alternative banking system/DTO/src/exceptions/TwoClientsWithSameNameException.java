package exceptions;

public class TwoClientsWithSameNameException extends Exception{
    public String name;

    public TwoClientsWithSameNameException(String name){
        this.name = name;
    }
    public void printMessage(){
        System.out.println("File is invalid! There are two clients with the name: "+ name);
    }
}
