package volki.soalab.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import volki.soalab.entities.dragon.DragonEntity;



@Repository
public interface DragonRepository extends CrudRepository<DragonEntity, Long> {

}
