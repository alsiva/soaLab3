package secondservice.businesslogic.exceptions;

import lombok.Getter;

public class EjbException extends Exception {
    @Getter
    private final int status;

    public EjbException(int status, String message) {
        super(message);
        this.status = status;
    }

}
