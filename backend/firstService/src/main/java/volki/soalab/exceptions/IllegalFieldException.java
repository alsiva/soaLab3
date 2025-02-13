package volki.soalab.exceptions;

public class IllegalFieldException extends IllegalParamException{
    public IllegalFieldException(String field) {
        super(String.format("Field (%s) doesn't exist", field));
    }

}
