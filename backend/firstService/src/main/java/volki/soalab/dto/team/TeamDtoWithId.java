package volki.soalab.dto.team;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import volki.soalab.entities.team.TeamEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "team")
@JsonPropertyOrder({"id"})
public class TeamDtoWithId extends TeamDto {

    @JacksonXmlProperty(localName = "id")
    @Setter
    private Long id;

    public TeamDtoWithId(TeamEntity teamEntity) {
        super(teamEntity);
        this.id = teamEntity.getId();
    }
}
