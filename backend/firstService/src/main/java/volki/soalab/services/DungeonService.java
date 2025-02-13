package volki.soalab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import volki.soalab.dto.dungeon.DungeonDto;
import volki.soalab.dto.dungeon.DungeonDtoWithId;
import volki.soalab.dto.dungeon.DungeonDtoWithIdList;
import volki.soalab.entities.dungeon.DungeonEntity;
import volki.soalab.repositories.DungeonRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DungeonService {

    private final DungeonRepository dungeonRepository;

    @Autowired
    public DungeonService(DungeonRepository dungeonRepository) {
        this.dungeonRepository = dungeonRepository;
    }

    public DungeonDtoWithIdList getDungeons() {
        List<DungeonDtoWithId> dungeonDtoWithIdList = ((List<DungeonEntity>) dungeonRepository.findAll())
                .stream().map(DungeonDtoWithId::new)
                .toList();
        return new DungeonDtoWithIdList(dungeonDtoWithIdList);
    }


    public ResponseEntity<DungeonDtoWithId> getDungeonById(long id) {
        return dungeonRepository.findById(id)
                .map(dungeon -> new ResponseEntity<>(new DungeonDtoWithId(dungeon), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public DungeonDtoWithId addDungeon(DungeonDto dungeonDto) {
        DungeonEntity dungeonEntity = new DungeonEntity(dungeonDto);
        return new DungeonDtoWithId(dungeonRepository.save(dungeonEntity));
    }

    public ResponseEntity<DungeonDtoWithId> deleteDungeonById(long id) {
        return dungeonRepository.findById(id)
                .map(dungeon -> {
                    DungeonDtoWithId deletedDungeon = new DungeonDtoWithId(dungeon);
                    dungeonRepository.deleteById(id);
                    return new ResponseEntity<>(deletedDungeon, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<DungeonDtoWithId> updateDungeonById(long id, DungeonDto dungeonDto) {
        Optional<DungeonEntity> dungeonEntityOptional = dungeonRepository.findById(id);
        if (dungeonEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DungeonEntity dungeonEntity = dungeonEntityOptional.get();
        dungeonEntity.updateByDungeonDto(dungeonDto);
        dungeonRepository.save(dungeonEntity);
        return ResponseEntity.ok(new DungeonDtoWithId(dungeonEntity));
    }

    public DungeonDtoWithId getMaxDungeon() {
        List<DungeonEntity> dungeonEntityList = (List<DungeonEntity>) dungeonRepository.findAll();
        dungeonEntityList.sort((a, b) -> (a.getSize() - b.getSize()) * -1);
        DungeonEntity maxEntity = dungeonEntityList.get(0);
        return new DungeonDtoWithId(maxEntity);
    }

    public DungeonDtoWithId getMinDungeon() {
        List<DungeonEntity> dungeonEntityList = (List<DungeonEntity>) dungeonRepository.findAll();
        dungeonEntityList.sort(Comparator.comparingInt(DungeonEntity::getSize));
        DungeonEntity maxEntity = dungeonEntityList.get(0);
        return new DungeonDtoWithId(maxEntity);
    }
}
