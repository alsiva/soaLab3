package volki.soalab.entities.team;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.dto.team.TeamDto;
import volki.soalab.dto.team.TeamDtoWithId;
import volki.soalab.entities.hunter.HunterEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "team")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="power")
    private Integer power;

    @OneToMany(mappedBy = "teamEntity")
    private List<HunterEntity> hunterEntityList;

    public TeamEntity(TeamDto teamDto) {
        this.name = teamDto.getName();
        this.power = teamDto.getPower();
        this.hunterEntityList = new ArrayList<>();
    }

    public TeamEntity(TeamDtoWithId teamDto) {
        this.name = teamDto.getName();
        this.power = teamDto.getPower();
        this.hunterEntityList = new ArrayList<>();
    }

    public void updateByTeamDto(TeamDto teamDto) {
        if (this.name != null) {
            this.name = teamDto.getName();
        }
        if (this.power != null) {
            this.power = teamDto.getPower();
        }
    }
}
