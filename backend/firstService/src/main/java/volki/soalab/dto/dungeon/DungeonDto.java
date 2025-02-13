package volki.soalab.dto.dungeon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.entities.dungeon.DungeonEntity;
import volki.soalab.exceptions.IllegalParamException;

@Data
@JacksonXmlRootElement(localName = "dungeon")
@NoArgsConstructor
public class DungeonDto {
    @NotNull(message = "Field cannot be null")
    @Min(value = 1, message = "Value must be greater than or equal to 1")
    @JacksonXmlProperty(localName = "size")
    private Integer size;

    @JacksonXmlProperty(localName = "dragonId")
    private Long dragonId;

    public DungeonDto(DungeonEntity dungeonEntity) {
        if (dungeonEntity.getSize() <= 0) {
            throw new IllegalParamException("Cave size should be more than 0");
        }
        this.size = dungeonEntity.getSize();
        this.dragonId = dungeonEntity.getDragonId();
    }
}
