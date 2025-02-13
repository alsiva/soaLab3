package volki.soalab.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import volki.soalab.dto.hunter.HunterDto;
import volki.soalab.dto.hunter.HunterDtoWithId;
import volki.soalab.dto.hunter.HunterDtoWithIdList;
import volki.soalab.services.HunterService;

import java.util.List;

@RestController
@RequestMapping("/hunters")
@Validated
public class HunterController {

    private final HunterService hunterService;

    @Autowired
    public HunterController(HunterService hunterService) {
        this.hunterService = hunterService;
    }
    @GetMapping(produces = "application/xml")
    public ResponseEntity<?> getHunters(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(required = false) List<String> sort,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long pageSize
    ) {
        HunterDtoWithIdList hunterDtoWithIdList = hunterService.getHunterList(filter, sort, page, pageSize);
        return ResponseEntity.ok(hunterDtoWithIdList);
    }

    @PutMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<?> updateHunterById(@PathVariable long id, @Valid @RequestBody HunterDto hunterDto) {
        return hunterService.updateById(id, hunterDto);
    }

    @GetMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<?> getHunterById(@PathVariable long id) {
        return hunterService.getHunterById(id);
    }

    @DeleteMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<?> deleteDragonById(@PathVariable long id) {
        return hunterService.deleteHunterById(id);
    }

    @PostMapping(produces = "application/xml", consumes = "application/xml")
    public HunterDtoWithId addHunter(@RequestBody @Valid HunterDtoWithId hunterDto) {
        return hunterService.addHunter(hunterDto);
    }
}
