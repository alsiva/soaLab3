package volki.soalab.dto.team;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;
import volki.soalab.dto.hunter.HunterDtoWithId;
import volki.soalab.dto.hunter.HunterDtoWithIdList;
import volki.soalab.entities.team.TeamEntity;




@Getter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "team")
@JsonPropertyOrder({"id", "name", "power"})
public class TeamDtoWithIdAndHunters extends TeamDtoWithId{

    @JacksonXmlProperty(localName = "hunterList")
    @Setter
    private HunterDtoWithIdList hunterDtoList;

    public TeamDtoWithIdAndHunters(TeamEntity teamEntity) {
        super(teamEntity);
        this.hunterDtoList = teamEntity.getHunterEntityList() != null ? new HunterDtoWithIdList(teamEntity.getHunterEntityList().stream().map(HunterDtoWithId::new).toList()) : null;
    }

}
