package Exception;

public class WrongFileFormatException extends RuntimeException {
    public WrongFileFormatException(String message) {
        super(message);
    }
}
