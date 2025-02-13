package volki.soalab.entities.hunter;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.dto.hunter.HunterDto;
import volki.soalab.dto.hunter.HunterDtoWithId;
import volki.soalab.entities.team.TeamEntity;

@Entity(name="hunter")
@Data
@NoArgsConstructor
public class HunterEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="strength")
    private Integer strength;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity teamEntity;

    public HunterEntity(HunterDto hunterDto) {
        this.firstName = hunterDto.getFirstName();
        this.lastName = hunterDto.getLastName();
        this.strength = hunterDto.getStrength();
        this.teamEntity = null;
    }

    public HunterEntity(HunterDtoWithId hunterDto) {
        this.id = hunterDto.getId();
        this.firstName = hunterDto.getFirstName();
        this.lastName = hunterDto.getLastName();
        this.strength = hunterDto.getStrength();
        this.teamEntity = null;
    }

    public HunterEntity(HunterDto hunterDto, TeamEntity teamEntity) {
        this.firstName = hunterDto.getFirstName();
        this.lastName = hunterDto.getLastName();
        this.strength = hunterDto.getStrength();
        this.teamEntity = teamEntity;
    }

    public void updateByHunterDto(HunterDto hunterDto, TeamEntity teamEntity) {
        this.firstName = hunterDto.getFirstName();
        this.lastName = hunterDto.getLastName();
        this.strength = hunterDto.getStrength();
        this.teamEntity = teamEntity;
    }
}
