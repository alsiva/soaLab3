package volki.soalab.dto.dragon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.entities.dragon.DragonHeadEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "head")
public class DragonHeadDto {

    @JacksonXmlProperty(localName = "eyesCount")
    private Long eyesCount;

    public DragonHeadDto(DragonHeadEntity dragonHeadEntity) {
        this.eyesCount = dragonHeadEntity != null ? dragonHeadEntity.getEyesCount() : null;
    }
}
