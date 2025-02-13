package volki.soalab.secondservice.DungeonDto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@XmlRootElement(name = "dungeon")
@NoArgsConstructor
@Data
@XmlType(propOrder = {"id", "size", "dragonId"})
@XmlAccessorType(XmlAccessType.FIELD)
public class DungeonDto {

    @Min(value = 1, message = "Id must be greater than 0")
    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "dragonId")
    private Long dragonId;

    @NotNull(message = "Field cannot be null")
    @Min(value = 1, message = "Value must be greater than or equal to 1")
    @XmlElement(name = "size")
    private Integer size;
}