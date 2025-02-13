package volki.soalab.controllers;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import volki.soalab.dto.dragon.*;
import volki.soalab.services.DragonService;
import volki.soalab.exceptions.IllegalParamException;

import java.util.List;

@RestController
@RequestMapping("/dragons")
@Validated
@Slf4j
public class DragonController {

    private final DragonService dragonService;

    @Autowired
    public DragonController(DragonService dragonService) {
        this.dragonService = dragonService;
    }

    @GetMapping(produces = "application/xml")
    public ResponseEntity<?> getDragons(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(required = false) List<String> sort,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long pageSize) {
        try {
            DragonDtoWithIdList dragonDtoWithIdList = dragonService.getDragons(
                    filter,
                    sort,
                    page,
                    pageSize
            );
            return ResponseEntity.ok(dragonDtoWithIdList);
        } catch (IllegalParamException e) {
            return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<DragonDtoWithId> getDragonById(@PathVariable long id) {
        return dragonService.getDragonById(id);
    }

    @PutMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<DragonDtoWithId> updateDragonById(@PathVariable long id, @Valid @RequestBody DragonDto dragonDto) {
        return dragonService.updateDragonById(id, dragonDto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<DragonDtoWithId> deleteDragonById(@PathVariable long id) {
        return dragonService.deleteDragonById(id);
    }

    @PostMapping(produces = "application/xml", consumes = "application/xml")
    public DragonDtoWithId addDragon(@RequestBody @Valid DragonDto dragonDto) {
        return dragonService.addDragon(dragonDto);
    }

    @GetMapping(value = "/count", produces = "application/xml")
    public ResponseEntity<DragonCountDto> countDragonsByEyes(@RequestParam long eyesCount) {
        DragonCountDto dto = dragonService.getDragonCountByEyesCount(eyesCount);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/count/{color}", produces = "application/xml")
    public ResponseEntity<DragonCountDto> countDragonsColor(@PathVariable String color) {
        DragonCountDto dragonCountDto = dragonService.getDragonByColorGreaterThen(color);
        return ResponseEntity.ok(dragonCountDto);
    }

    @GetMapping(value = "/search/{substring}", produces = "application/xml")
    public ResponseEntity<?> getDragonsBySubName(@PathVariable String substring) {
        DragonDtoWithIdList dragonDtoWithIdList = dragonService.getDragonsWhereNameContains(substring);
        return ResponseEntity.ok(dragonDtoWithIdList);
    }


}
