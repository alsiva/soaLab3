package volki.soalab.secondservice.DragonDto;

import jakarta.xml.bind.annotation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@XmlRootElement(name = "dragon") // Указывает, что это корневой элемент в XML
@XmlAccessorType(XmlAccessType.FIELD) // Позволяет JAXB работать с полями класса
@NoArgsConstructor
public class DragonDto {

    @Min(value = 1, message = "Id must be greater than 0")
    @XmlElement(name = "id") // Указываем имя элемента в XML
    private Long id;

    // name не может быть null и пустым
    @NotNull
    @NotEmpty
    @XmlElement(name = "name") // Имя элемента в XML
    private String name;

    // coordinates не может быть null
    @NotNull(message = "Coordinates cannot be null")
    @XmlElement(name = "coordinates") // Имя элемента в XML
    private CoordinatesDto coordinates;

    // creationDate генерируется автоматически, не может быть null
    @XmlElement(name = "creationDateRolex") // Имя элемента в XML
    @XmlJavaTypeAdapter(DragonDateAdapter.class)
    private LocalDateTime creationDate;

    // age должно быть больше 0, может быть null
    @Min(value = 1, message = "Age must be greater than 0")
    @XmlElement(name = "age") // Имя элемента в XML
    private Long age;

    // wingspan должно быть больше 0, может быть null
    @Min(value = 1, message = "Wingspan must be greater than 0")
    @XmlElement(name = "wingspan") // Имя элемента в XML
    private Float wingspan;

    // speaking (без дополнительных ограничений)
    @XmlElement(name = "speaking") // Имя элемента в XML
    private Boolean speaking;

    // color не может быть null
    @NotNull(message = "Color cannot be null")
    @XmlElement(name = "color") // Имя элемента в XML
    private ColorDto color;

    // head (без дополнительных ограничений)
    @XmlElement(name = "head") // Имя элемента в XML
    private DragonHeadDto head;

}
