package volki.soalab.controllers;

import jakarta.validation.Valid;
import jakarta.ws.rs.Produces;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import volki.soalab.dto.dungeon.DungeonDto;
import volki.soalab.dto.dungeon.DungeonDtoWithId;
import volki.soalab.dto.dungeon.DungeonDtoWithIdList;
import volki.soalab.services.DungeonService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/dungeons")
@Validated
public class DungeonController {

    private final DungeonService dungeonService;

    public DungeonController(DungeonService dungeonService) {
        this.dungeonService = dungeonService;
    }

    @GetMapping(produces = "application/xml")
    public DungeonDtoWithIdList getDungeons() {
        return dungeonService.getDungeons();
    }

    @GetMapping(value = "/max", produces = "application/xml")
    public DungeonDtoWithId getMaxDungeon() {
        return dungeonService.getMaxDungeon();
    }

    @GetMapping(value = "/min", produces = "application/xml")
    public DungeonDtoWithId getMinDungeon() {
        return dungeonService.getMinDungeon();
    }

    @GetMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<DungeonDtoWithId> getDungeonById(@PathVariable long id) {
        return dungeonService.getDungeonById(id);
    }

    @DeleteMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<DungeonDtoWithId> deleteDungeonById(@PathVariable long id) {
        return dungeonService.deleteDungeonById(id);
    }

    @PutMapping(value = "/{id}", produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<DungeonDtoWithId> updateDungeonById(@PathVariable long id, @Valid @RequestBody DungeonDto dungeonDto) {
        return dungeonService.updateDungeonById(id, dungeonDto);
    }

    @PostMapping(produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<DungeonDtoWithId> addDungeon(@RequestBody @Valid DungeonDto dungeonDto) {
        return ResponseEntity.ok(dungeonService.addDungeon(dungeonDto));
    }
}
