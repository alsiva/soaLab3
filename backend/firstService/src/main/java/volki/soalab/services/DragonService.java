package volki.soalab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import volki.soalab.dto.dragon.*;
import volki.soalab.repositories.DragonRepository;
import volki.soalab.entities.dragon.DragonEntity;
import volki.soalab.dtoManipulator.DtoListManipulator;
import volki.soalab.exceptions.IllegalParamException;

import java.util.List;
import java.util.Optional;


@Service
public class DragonService {

    private final DragonRepository dragonRepository;
    private final DtoListManipulator dtoListManipulator;

    @Autowired
    public DragonService(DragonRepository dragonRepository, DtoListManipulator dtoListManipulator) {
        this.dragonRepository = dragonRepository;
        this.dtoListManipulator = dtoListManipulator;
    }


    public DragonDtoWithIdList getDragons(List<String> filter, List<String> sort, Long page, Long pageSize) {
        List<DragonDtoWithId> dragonDtoWithIdList = ((List<DragonEntity>) dragonRepository.findAll())
                .stream().map(DragonDtoWithId::new)
                .toList();

        if (filter != null && !filter.isEmpty()) {
            dragonDtoWithIdList = dtoListManipulator.filterDragons(dragonDtoWithIdList, filter);
        }

        if (sort != null && !sort.isEmpty()) {
            dragonDtoWithIdList = dtoListManipulator.sortDragon(dragonDtoWithIdList, sort);
        }

        if (page != null && pageSize != null) {
            dragonDtoWithIdList = dtoListManipulator.page(dragonDtoWithIdList, page, pageSize);
        }

        return new DragonDtoWithIdList(dragonDtoWithIdList);
    }

    public DragonDtoWithId addDragon(DragonDto dragonDto) {

        DragonEntity dragonEntity = new DragonEntity(dragonDto);
        return new DragonDtoWithId(dragonRepository.save(dragonEntity));
    }

    public ResponseEntity<DragonDtoWithId> getDragonById(long id) {
        return dragonRepository.findById(id)
                .map(dragon -> new ResponseEntity<>(new DragonDtoWithId(dragon), HttpStatus.OK))  // Если дракон найден
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));  // Если дракон не найден
    }

    public ResponseEntity<DragonDtoWithId> deleteDragonById(long id) {
        return dragonRepository.findById(id)
                .map(dragon -> {
                    DragonDtoWithId deletedDragon = new DragonDtoWithId(dragon);  // Создаем DTO удаленного дракона
                    dragonRepository.deleteById(id);  // Удаляем дракона
                    return new ResponseEntity<>(deletedDragon, HttpStatus.OK);  // Возвращаем удаленного дракона
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));  // Если дракон не найден

    }

    public DragonCountDto getDragonCountByEyesCount(long eyesCount) {
        return new DragonCountDto(
                ((List<DragonEntity>) dragonRepository.findAll())
                        .stream().filter(dragonEntity -> dragonEntity.getHead() != null && dragonEntity.getHead().getEyesCount() == eyesCount)
                        .count()
        );
    }

    public DragonCountDto getDragonByColorGreaterThen(String color) {
        ColorDto colorToCompare;
        try {
            colorToCompare = ColorDto.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalParamException(String.format(
                    "Color (%s) doesn't exist", color.toUpperCase()
            ));
        }

        return new DragonCountDto(
                ((List<DragonEntity>) dragonRepository.findAll())
                        .stream().map(DragonEntity::getColorEntity)
                        .map(ColorDto::fromEntity)
                        .filter(colorDto -> colorDto.compareTo(colorToCompare) >= 0)
                        .count()
        );
    }

    public DragonDtoWithIdList getDragonsWhereNameContains(String substring) {
        List<DragonDtoWithId> dragons = ((List<DragonEntity>) dragonRepository.findAll())
                        .stream().map(DragonDtoWithId::new)
                        .filter(dragonDtoWithId -> dragonDtoWithId.getName().contains(substring))
                        .toList();
        return new DragonDtoWithIdList(dragons);
    }


    public ResponseEntity<DragonDtoWithId> updateDragonById(long id, DragonDto dragonDto) {
        Optional<DragonEntity> dragonEntityOptional = dragonRepository.findById(id);
        if (dragonEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DragonEntity dragonEntity = dragonEntityOptional.get();
        dragonEntity.updateByDragonDto(dragonDto);
        dragonRepository.save(dragonEntity);
        return ResponseEntity.ok(new DragonDtoWithId(dragonEntity));

    }
}
