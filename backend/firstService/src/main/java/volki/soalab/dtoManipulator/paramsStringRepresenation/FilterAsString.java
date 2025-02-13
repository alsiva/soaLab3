package volki.soalab.dtoManipulator.paramsStringRepresenation;

import lombok.AllArgsConstructor;
import lombok.Data;
import volki.soalab.exceptions.IllegalParamException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class FilterAsString {
    private String field;
    private String operator;
    private String value;

    public FilterAsString(String filterAsString) {
        String filterRegex = "^([a-zA-Z_][a-zA-Z0-9_]*)-(eq|nq|gt|lt|ge|le)-(.+)$";
        Pattern patter = Pattern.compile(filterRegex);


        Matcher matcher = patter.matcher(filterAsString);
        if (!matcher.matches()) {
            throw new IllegalParamException(
                    String.format("Filter (%s) doesn't match field-value-operator pattern", filterAsString)
            );
        }
        this.field = matcher.group(1);
        this.operator = matcher.group(2);
        this.value = matcher.group(3);
    }
}
