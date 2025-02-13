package volki.soalab.controllers;

import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import volki.soalab.dto.team.TeamDto;
import volki.soalab.dto.team.TeamDtoWithId;
import volki.soalab.dto.team.TeamDtoWithIdAndHunters;
import volki.soalab.dto.team.TeamDtoWithIdList;
import volki.soalab.services.TeamService;

import java.util.List;

@RestController
@RequestMapping("/teams")
@Validated
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(produces = "application/xml")
    public ResponseEntity<TeamDtoWithIdList> getTeams(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(required = false) List<String> sort,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long pageSize
    ) {
        TeamDtoWithIdList teamDtoWithIdList = teamService.getTeams(filter, sort, page, pageSize);
        return ResponseEntity.ok(teamDtoWithIdList);
    }

    @GetMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<TeamDtoWithIdAndHunters> getTeamById(@PathVariable long id) {
        return teamService.getTeamById(id);
    }


    @PostMapping(produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<TeamDtoWithId> addTeam(@RequestBody @Valid TeamDtoWithId teamDto) {
        return teamService.addTeam(teamDto);
    }

    @PutMapping(value = "/{id}", produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<TeamDtoWithIdAndHunters> updateTeam(@PathVariable long id, @RequestBody @Valid TeamDto teamDto) {
        return teamService.updateTeam(id, teamDto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<TeamDtoWithIdAndHunters> deleteTeam(@PathVariable long id) {
        return teamService.deleteTeam(id);
    }
}
