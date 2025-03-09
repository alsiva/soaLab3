package secondservice.businesslogic.exceptions;

public class CannotCreateHttpClientException extends Exception {
    public CannotCreateHttpClientException(Throwable cause) {
        super("Cannot create http client", cause);
    }
}
