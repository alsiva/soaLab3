package volki.soalab.dtoManipulator.manipulationMachines;

import org.springframework.stereotype.Component;
import volki.soalab.dto.hunter.HunterDtoWithId;
import volki.soalab.dto.team.TeamDtoWithId;
import volki.soalab.dtoManipulator.fields.DragonFields;
import volki.soalab.dtoManipulator.fields.HunterFields;
import volki.soalab.dtoManipulator.fields.TeamFields;
import volki.soalab.dtoManipulator.paramsStringRepresenation.SortAsString;
import volki.soalab.dto.dragon.ColorDto;
import volki.soalab.dto.dragon.CoordinatesDto;
import volki.soalab.dto.dragon.DragonDtoWithId;
import volki.soalab.exceptions.IllegalFieldException;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SortMachine {

    public List<DragonDtoWithId> dragonSort(List<DragonDtoWithId> dragonDtoWithIdList, List<SortAsString> sortAsStringList) {
        return dragonDtoWithIdList.stream().sorted(
                ((a, b) -> dragonCompare(a, b, sortAsStringList))
        ).toList();
    }

    public List<HunterDtoWithId> hunterSort(List<HunterDtoWithId> hunterDtoWithIdList, List<SortAsString> sortAsStringList) {
        return hunterDtoWithIdList.stream().sorted(
                ((a, b) -> hunterCompare(a, b, sortAsStringList))
        ).toList();
    }

    public List<TeamDtoWithId> teamSort(List<TeamDtoWithId> teamDtoWithIdList, List<SortAsString> sortAsStringList) {
        return teamDtoWithIdList.stream().sorted(
                ((a, b) -> teamCompare(a, b, sortAsStringList))
        ).toList();
    }

    private <T extends Comparable<T>> int genericCompare(T a, T b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a != null && b == null) {
            return 1;
        }
        if (a == null && b != null) {
            return -1;
        }

        return a.compareTo(b);

    }

    private int dragonCompare(DragonDtoWithId a, DragonDtoWithId b, List<SortAsString> sortAsStringList) {
        for (SortAsString sortAsString: sortAsStringList) {
            String field = sortAsString.getField();
            DragonFields dragonFields = DragonFields.fromString(field);
            int compareResult;

            switch (dragonFields) {
                case id:
                    Long firstDragonId = a.getId();
                    Long secondDragonId = b.getId();
                    compareResult = genericCompare(firstDragonId, secondDragonId);
                    break;
                case name:
                    String firstDragonName = a.getName();
                    String secondDragonName = b.getName();
                    compareResult = genericCompare(firstDragonName, secondDragonName);
                    break;
                case eyescount:
                    Long firstEyesCount = a.getHead().getEyesCount();
                    Long secondEyesCount = b.getHead().getEyesCount();
                    compareResult = genericCompare(firstEyesCount, secondEyesCount);
                    break;
                case speaking:
                    Boolean firstIsSpeaking = a.getSpeaking();
                    Boolean secondIsSpeaking = b.getSpeaking();
                    compareResult = genericCompare(firstIsSpeaking, secondIsSpeaking);
                    break;
                case wingspan:
                    Float firstWingSpan = a.getWingspan();
                    Float secondWingSpan = b.getWingspan();
                    compareResult = genericCompare(firstWingSpan, secondWingSpan);
                    break;
                case age:
                    Long firstAge = a.getAge();
                    Long secondAge = b.getAge();
                    compareResult = genericCompare(firstAge, secondAge);
                    break;
                case color:
                    ColorDto firstColorDto = a.getColor();
                    ColorDto secondColorDto = b.getColor();
                    compareResult = firstColorDto.compareTo(secondColorDto);
                    break;
                case coordinates:
                    CoordinatesDto firstCoordinates = a.getCoordinates();
                    CoordinatesDto secondCoordinates = b.getCoordinates();
                    compareResult = genericCompare(firstCoordinates, secondCoordinates);
                    break;
                case creationdate:
                    LocalDateTime firstCreationDate = a.getCreationDate();
                    LocalDateTime secondCreationDate = b.getCreationDate();
                    compareResult = genericCompare(firstCreationDate, secondCreationDate);
                    break;
                default:
                    throw new IllegalArgumentException(
                            String.format("Field (%s) doesn't exist", field)
                    );
            }

            if (compareResult != 0) {
                return sortAsString.getAsc() ? compareResult : -compareResult;
            }

        }
        return 0;
    }

    private int hunterCompare(HunterDtoWithId a, HunterDtoWithId b, List<SortAsString> sortAsStringList) {
        for (SortAsString sortAsString: sortAsStringList) {
            String field = sortAsString.getField();
            HunterFields hunterFields = HunterFields.fromString(field);
            int compareResult;

            switch (hunterFields) {
                case id -> compareResult = genericCompare(a.getId(), b.getId());
                case firstName -> compareResult = genericCompare(a.getFirstName(), b.getFirstName());
                case lastName -> compareResult = genericCompare(a.getLastName(), b.getLastName());
                case strength -> compareResult = genericCompare(a.getStrength(), b.getStrength());
                case teamId -> compareResult = genericCompare(a.getTeamId(), b.getTeamId());
                default -> throw new IllegalFieldException(field);
            }

            if (compareResult != 0) {
                return sortAsString.getAsc() ? compareResult : -compareResult;
            }
        }

        return 0;
    }

    private int teamCompare(TeamDtoWithId a, TeamDtoWithId b, List<SortAsString> sortAsStringList) {
        for (SortAsString sortAsString: sortAsStringList) {
            String field = sortAsString.getField();
            TeamFields teamFields = TeamFields.fromString(field);
            int compareResult;

            switch (teamFields) {
                case id -> compareResult = genericCompare(a.getId(), b.getId());
                case name -> compareResult = genericCompare(a.getName(), b.getName());
                case power -> compareResult = genericCompare(a.getPower(), b.getPower());
                default -> throw new IllegalFieldException(field);
            }

            if (compareResult != 0) {
                return sortAsString.getAsc() ? compareResult : -compareResult;
            }
        }

        return 0;
    }





}
