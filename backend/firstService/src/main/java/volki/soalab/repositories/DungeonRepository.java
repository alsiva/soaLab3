package volki.soalab.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import volki.soalab.entities.dungeon.DungeonEntity;

import java.util.List;

@Repository
public interface DungeonRepository extends CrudRepository<DungeonEntity, Long> {

}
