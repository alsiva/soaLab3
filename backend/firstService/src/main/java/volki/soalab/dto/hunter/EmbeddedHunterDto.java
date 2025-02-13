package volki.soalab.dto.hunter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import volki.soalab.entities.hunter.HunterEntity;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class EmbeddedHunterDto {
    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "firstName")
    private String firstName;

    @XmlElement(name = "TriedSomethingNew")
    private String lastNameChanged;

    @XmlElement(name = "strength")
    private int strength;

    public EmbeddedHunterDto(HunterEntity hunterEntity) {
        this.id = hunterEntity.getId();
        this.firstName = hunterEntity.getFirstName();
        this.lastNameChanged = hunterEntity.getLastName();
        this.strength = hunterEntity.getStrength();
    }
}
