package volki.soalab.dto.team;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.entities.team.TeamEntity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "team")
public class TeamDto {


    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "power")
    private Integer power;


    public TeamDto(TeamEntity teamEntity) {
        this.name = teamEntity.getName();
        this.power = teamEntity.getPower();
    }
}
