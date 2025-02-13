package volki.soalab.repositories;

import org.springframework.data.repository.CrudRepository;
import volki.soalab.entities.team.TeamEntity;

public interface TeamRepository extends CrudRepository<TeamEntity, Long> {
}
