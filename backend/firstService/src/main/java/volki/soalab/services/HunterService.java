package volki.soalab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import volki.soalab.dto.dragon.DragonDto;
import volki.soalab.dto.dragon.DragonDtoWithId;
import volki.soalab.dto.hunter.HunterDto;
import volki.soalab.dto.hunter.HunterDtoWithId;
import volki.soalab.dto.hunter.HunterDtoWithIdList;
import volki.soalab.dtoManipulator.DtoListManipulator;
import volki.soalab.entities.dragon.DragonEntity;
import volki.soalab.entities.hunter.HunterEntity;
import volki.soalab.entities.team.TeamEntity;
import volki.soalab.exceptions.IllegalParamException;
import volki.soalab.repositories.HunterRepository;
import volki.soalab.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class HunterService {

    private final HunterRepository hunterRepository;
    private final TeamRepository teamRepository;
    private final DtoListManipulator dtoListManipulator;

    @Autowired
    public HunterService(HunterRepository hunterRepository, TeamRepository teamRepository, DtoListManipulator dtoListManipulator) {
        this.teamRepository = teamRepository;
        this.hunterRepository = hunterRepository;
        this.dtoListManipulator = dtoListManipulator;
    }

    public HunterDtoWithIdList getHunterList(List<String> filter, List<String> sort, Long page, Long pageSize) {
        List<HunterDtoWithId> hunterDtoWithIdList = (((List<HunterEntity>) hunterRepository.findAll()).stream()
                .map(HunterDtoWithId::new)
                .toList());

        if (filter != null && !filter.isEmpty()) {
            hunterDtoWithIdList = dtoListManipulator.filterHunters(hunterDtoWithIdList, filter);
        }

        if (sort != null && !sort.isEmpty()) {
            hunterDtoWithIdList = dtoListManipulator.sortHunter(hunterDtoWithIdList, sort);
        }

        if (page != null && pageSize != null) {
            hunterDtoWithIdList = dtoListManipulator.page(hunterDtoWithIdList, page, pageSize);
        }

        return new HunterDtoWithIdList(hunterDtoWithIdList);

    }

    public ResponseEntity<HunterDtoWithId> getHunterById(long id) {
        return hunterRepository.findById(id)
                .map(hunterEntity -> new ResponseEntity<>(new HunterDtoWithId(hunterEntity), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<?> deleteHunterById(long id) {
        return hunterRepository.findById(id)
                .map(hunterEntity -> {
                    HunterDtoWithId deletedHunter = new HunterDtoWithId(hunterEntity);
                    hunterRepository.deleteById(id);
                    return new ResponseEntity<>(deletedHunter, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public HunterDtoWithId addHunter(HunterDtoWithId hunterDto) {

        Long teamId = hunterDto.getTeamId();
        if (teamId == null) {
            return new HunterDtoWithId(hunterRepository.save(new HunterEntity(hunterDto)));
        }

        Optional<TeamEntity> teamEntityOptional = teamRepository.findById(hunterDto.getTeamId());
        if (teamEntityOptional.isEmpty()) {
            throw new IllegalParamException(
                    String.format("Team with id (%s) doesn't exist", hunterDto.getTeamId())
            );
        }

        TeamEntity teamEntity = teamEntityOptional.get();
        HunterEntity hunterEntity = new HunterEntity(hunterDto, teamEntity);

        teamEntity.getHunterEntityList().add(hunterEntity);
        teamRepository.save(teamEntity);

        return new HunterDtoWithId(hunterRepository.save(hunterEntity));
    }

    public ResponseEntity<HunterDtoWithId> updateById(long id, HunterDto hunterDto) {
        Optional<HunterEntity> hunterEntityOptional = hunterRepository.findById(id);
        if (hunterEntityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HunterEntity hunterEntity = hunterEntityOptional.get();

        if (hunterDto.getTeamId() == null) {
            hunterEntity.updateByHunterDto(hunterDto, hunterEntity.getTeamEntity());
        } else {
            Optional<TeamEntity> teamEntityOptional = teamRepository.findById(hunterDto.getTeamId());
            if (teamEntityOptional.isEmpty()) {
                throw new IllegalParamException(String.format(
                        """
                        TeamEntity with id (%d) doesn't exist
                        """,  hunterDto.getTeamId()
                ));
            }
            TeamEntity teamEntity = teamEntityOptional.get();

            hunterEntity.updateByHunterDto(hunterDto, teamEntity);
            hunterRepository.save(hunterEntity);
        }


        return ResponseEntity.ok(new HunterDtoWithId(hunterEntity));
    }
}
