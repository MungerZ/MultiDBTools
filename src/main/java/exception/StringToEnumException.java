package exception;

public class StringToEnumException extends RuntimeException {
    public void printExceptionInfo(){
        System.out.println("Exception occur: String convert to Enum error, please check input String.");
    }
}
