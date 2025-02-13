package volki.soalab.dto.dragon;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import volki.soalab.entities.dragon.DragonEntity;
import volki.soalab.entities.dungeon.DungeonEntity;

@Getter
@Setter
@JacksonXmlRootElement(localName = "dragon")
@NoArgsConstructor
@JsonPropertyOrder({"id"})
public class DragonDtoWithId extends DragonDto {

    // id должно быть больше 0, генерируется автоматически
    @Min(value = 1, message = "Id must be greater than 0")
    @JacksonXmlProperty(localName = "id")
    private Long id;

    public DragonDtoWithId(DragonEntity dragonEntity) {
        super(dragonEntity);
        this.id = dragonEntity.getId();
    }

}
