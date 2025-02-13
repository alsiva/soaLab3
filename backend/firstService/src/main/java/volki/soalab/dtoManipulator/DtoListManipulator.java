package volki.soalab.dtoManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import volki.soalab.dto.hunter.HunterDtoWithId;
import volki.soalab.dto.team.TeamDtoWithId;
import volki.soalab.dto.team.TeamDtoWithIdList;
import volki.soalab.dtoManipulator.manipulationMachines.FilterMachine;
import volki.soalab.dtoManipulator.manipulationMachines.SortMachine;
import volki.soalab.dtoManipulator.paramsStringRepresenation.FilterAsString;
import volki.soalab.dtoManipulator.paramsStringRepresenation.SortAsString;
import volki.soalab.dto.dragon.DragonDtoWithId;
import volki.soalab.exceptions.IllegalParamException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoListManipulator {

    private final FilterMachine filterMachine;
    private final SortMachine sortMachine;

    @Autowired
    public DtoListManipulator(FilterMachine filterMachine, SortMachine sortMachine) {
        this.filterMachine = filterMachine;
        this.sortMachine = sortMachine;
    }

    public List<DragonDtoWithId> filterDragons(List<DragonDtoWithId> dragonDtoWithIdList, List<String> filterListAsString) {
        List<FilterAsString> filterList = filterListAsString.stream()
                .map(FilterAsString::new)
                .toList();
        return dragonDtoWithIdList.stream()
                .filter(dragonDto -> filterList.stream()
                        .allMatch(filterAsString -> filterMachine.dragonMatches(dragonDto, filterAsString)))
                .collect(Collectors.toList());
    }

    public List<HunterDtoWithId> filterHunters(List<HunterDtoWithId> hunterDtoWithIdList, List<String> filterListAsString) {
        List<FilterAsString> filterList = filterListAsString.stream()
                .map(FilterAsString::new)
                .toList();
        return hunterDtoWithIdList.stream()
                .filter(hunterDto -> filterList.stream()
                        .allMatch(filterAsString -> filterMachine.hunterMatches(hunterDto, filterAsString)))
                .collect(Collectors.toList());
    }

    public List<TeamDtoWithId> filterTeams(List<TeamDtoWithId> teamDtoWithIdListList, List<String> filterListAsString) {
        List<FilterAsString> filterList = filterListAsString.stream()
                .map(FilterAsString::new)
                .toList();
        return teamDtoWithIdListList.stream()
                .filter(teamDto -> filterList.stream()
                        .allMatch(filterAsString -> filterMachine.teamMatches(teamDto, filterAsString)))
                .collect(Collectors.toList());
    }

    public <T> List<T> page(List<T> dtoList, Long page, Long pageSize) {
        if (page < 0 || pageSize < 1) {
            throw new IllegalParamException("Page number and size should be more than or equal 1");
        }
        if(page == 0){
            return dtoList;
        }
        return dtoList.stream()
                .skip((page-1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    public List<DragonDtoWithId> sortDragon(List<DragonDtoWithId> dragonDtoWithIdList, List<String> sortListAsString) {
        List<SortAsString> sortAsString = sortListAsString.stream().map(SortAsString::new).toList();
        return sortMachine.dragonSort(dragonDtoWithIdList, sortAsString);
    }

    public List<HunterDtoWithId> sortHunter(List<HunterDtoWithId> hunterDtoWithIdList, List<String> sortListAsString) {
        List<SortAsString> sortAsString = sortListAsString.stream().map(SortAsString::new).toList();
        return sortMachine.hunterSort(hunterDtoWithIdList, sortAsString);
    }

    public List<TeamDtoWithId> sortTeam(List<TeamDtoWithId> teamDtoWithIdList, List<String> sortListAsString) {
        List<SortAsString> sortAsString = sortListAsString.stream().map(SortAsString::new).toList();
        return sortMachine.teamSort(teamDtoWithIdList, sortAsString);
    }

}
