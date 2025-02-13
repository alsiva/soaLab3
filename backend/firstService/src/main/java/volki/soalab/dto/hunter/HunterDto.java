package volki.soalab.dto.hunter;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.entities.hunter.HunterEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "hunter") // Указание корневого элемента
public class HunterDto {

    @NotNull(message = "FirstName cannot be null")
    @NotEmpty(message = "FirstName cannot be empty")
    @JacksonXmlProperty(localName = "firstName") // Указание имени элемента
    private String firstName;

    @NotNull(message = "LastName cannot be null")
    @NotEmpty(message = "LastName cannot be empty")
    @JacksonXmlProperty(localName = "lastName") // Указание имени элемента
    private String lastName;

    @NotNull(message = "Strength cannot be null")
    @JacksonXmlProperty(localName = "strength") // Указание имени элемента
    private Integer strength;

    @JacksonXmlProperty(localName = "teamId") // Указание имени элемента
    private Long teamId;

    // Конструктор для преобразования из HunterEntity
    public HunterDto(HunterEntity hunterEntity) {
        this.firstName = hunterEntity.getFirstName();
        this.lastName = hunterEntity.getLastName();
        this.strength = hunterEntity.getStrength();
        if (hunterEntity.getTeamEntity() != null) {
            this.teamId = hunterEntity.getTeamEntity().getId();
        }
    }
}
