package volki.soalab.secondservice.DragonDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "coordinates") // JAXB эквивалент @JacksonXmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CoordinatesDto {

    @Max(value = 411, message = "x must be lower or equal than 411")
    @NotNull(message = "x cannot be null")
    @NotEmpty(message = "x cannot be empty")
    @XmlElement(name = "x") // JAXB эквивалент @JacksonXmlProperty
    private Double x; // Максимальное значение поля: 411, Поле не может быть null

    @Min(value = -296, message = "y must be greater or equal than -296")
    @XmlElement(name = "y") // JAXB эквивалент @JacksonXmlProperty
    private Long y; // Значение поля должно быть больше -296
}
