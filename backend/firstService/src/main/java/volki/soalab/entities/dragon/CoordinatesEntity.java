package volki.soalab.entities.dragon;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.dto.dragon.CoordinatesDto;

@Embeddable
@Data
@NoArgsConstructor
@Valid
public class CoordinatesEntity {
    @Column(name = "coordinate_x")
    @NotNull
    private Double x; //Максимальное значение поля: 411, Поле не может быть null
    @Column(name = "coordinate_y")
    private long y; //Значение поля должно быть больше -296

    public CoordinatesEntity(CoordinatesDto coordinatesDto) {
        this.x = coordinatesDto.getX();
        this.y = coordinatesDto.getY();
    }

    public void updateByDto(CoordinatesDto coordinatesDto) {
        this.x = coordinatesDto.getX();
        this.y = coordinatesDto.getY();

    }
}
