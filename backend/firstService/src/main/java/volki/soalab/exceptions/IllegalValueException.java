package volki.soalab.exceptions;

public class IllegalValueException extends IllegalParamException{
    public IllegalValueException(String field, String value, Class<?> type) {
        super(String.format("Value (%s) doesn't match field (%s) with type (%s)", value, field, type.getSimpleName()));
    }
}
