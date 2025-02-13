package volki.soalab.dtoManipulator.fields;

import volki.soalab.exceptions.IllegalParamException;

public enum TeamFields {
    id,
    name,
    power;

    public static TeamFields fromString(String s) {
        try {
            return TeamFields.valueOf(s.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalParamException(
                    String.format("Field (%s) doesn't exist", s.toLowerCase())
            );
        }
    }
}
