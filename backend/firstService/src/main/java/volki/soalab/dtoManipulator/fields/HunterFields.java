package volki.soalab.dtoManipulator.fields;

import volki.soalab.exceptions.IllegalParamException;

public enum HunterFields {
    id,
    firstName,
    lastName,
    strength,
    teamId;

    public static HunterFields fromString(String s) {
        try {
            return HunterFields.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new IllegalParamException(
                    String.format("Field (%s) doesn't exist", s)
            );
        }
    }
}

