package volki.soalab.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import volki.soalab.entities.hunter.HunterEntity;

@Repository
public interface HunterRepository extends CrudRepository<HunterEntity, Long> {
}
