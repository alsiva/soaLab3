package volki.soalab.dtoManipulator.paramsStringRepresenation;


import lombok.Data;
import volki.soalab.exceptions.IllegalParamException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class SortAsString {
    private String field;
    private Boolean asc;


    public SortAsString(String sortAsString) {
        String sortRegex = "(-)?([a-zA-Z]+)";
        Pattern pattern = Pattern.compile(sortRegex);

        Matcher matcher = pattern.matcher(sortAsString);
        if (!matcher.matches()) {
            throw new IllegalParamException(
                    String.format("Sort (%s) doesn't match -value or value pattern", sortAsString)
            );
        }
        this.asc = matcher.group(1) == null;
        this.field = matcher.group(2);
    }
}
