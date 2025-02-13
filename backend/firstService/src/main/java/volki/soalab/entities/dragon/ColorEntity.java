package volki.soalab.entities.dragon;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.dto.dragon.ColorDto;

@Embeddable
@Data
@NoArgsConstructor
public class ColorEntity {
    @Column(name = "color")
    private String name;
    public ColorEntity(ColorDto colorDto) {
        this.name = colorDto.name().toUpperCase();
    }

    public void updateByDto(ColorDto colorDto) {
        this.name = colorDto.name().toUpperCase();
    }

}
