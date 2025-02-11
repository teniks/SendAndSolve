package su.sendandsolve.server.data.exception;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException() {
        super("Already exists"); // Сообщение по умолчанию
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
