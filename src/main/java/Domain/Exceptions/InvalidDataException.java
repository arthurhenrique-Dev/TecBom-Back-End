package Domain.Exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
    public InvalidDataException() {
        super("dados inválidos inseridos");
    }
}
