package volki.soalab.entities.dragon;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.dto.dragon.DragonHeadDto;

@Embeddable
@Data
@NoArgsConstructor
public class DragonHeadEntity {

    @Column(name = "eyes_count")
    private long eyesCount;

    public DragonHeadEntity(DragonHeadDto dragonHeadDto) {
        this.eyesCount = dragonHeadDto.getEyesCount();
    }

    public void updateByDto(DragonHeadDto dragonHeadDto) {
        this.eyesCount = dragonHeadDto.getEyesCount();
    }
}
