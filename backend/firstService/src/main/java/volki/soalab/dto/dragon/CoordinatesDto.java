package volki.soalab.dto.dragon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.entities.dragon.CoordinatesEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "coordinates")
public class CoordinatesDto implements Comparable<CoordinatesDto> {

    @Max(value = 411, message = "x must be lower or equal than 411")
    @NotNull(message = "x cannot be null")
    @JacksonXmlProperty(localName = "x")
    private Double x; // Максимальное значение поля: 411, Поле не может быть null

    @Min(value = -296, message = "y must be greater or equal than -296")
    @JacksonXmlProperty(localName = "y")
    private Long y; // Значение поля должно быть больше -296

    public CoordinatesDto(String coordinates) {
        String coordRegex = "^(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?)$";
        Pattern pattern = Pattern.compile(coordRegex);
        Matcher matcher = pattern.matcher(coordinates);

        if (matcher.matches()) {
            this.x = Double.parseDouble(matcher.group(1));
            this.y = Long.parseLong(matcher.group(3));
        } else {
            throw new IllegalArgumentException("Invalid coordinate format");
        }
    }

    public CoordinatesDto(CoordinatesEntity coordinatesEntity) {
        this.x = coordinatesEntity.getX();
        this.y = coordinatesEntity.getY();
    }

    private double countDistance() {
        return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
    }

    @Override
    public int compareTo(CoordinatesDto o) {
        return Double.compare(this.countDistance(), o.countDistance());
    }
}
