package volki.soalab.entities.dungeon;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import volki.soalab.dto.dungeon.DungeonDto;

@Entity(name="dungeon")
@Data
@NoArgsConstructor
public class DungeonEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(name = "size")
    private int size; //Значение поля должно быть больше 0

    @Column(name = "dragon_id")
    private Long dragonId;

    public void updateByDungeonDto(DungeonDto dungeonDto) {
        this.size = dungeonDto.getSize();
        this.dragonId = dungeonDto.getDragonId();
    }

    public DungeonEntity(DungeonDto dungeonDto) {
        this.size = dungeonDto.getSize();
    }
}
