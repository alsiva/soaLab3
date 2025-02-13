package volki.soalab.dtoManipulator.manipulationMachines;

import org.springframework.stereotype.Component;
import volki.soalab.dto.hunter.HunterDto;
import volki.soalab.dto.hunter.HunterDtoWithId;
import volki.soalab.dto.team.TeamDtoWithId;
import volki.soalab.dtoManipulator.fields.DragonFields;
import volki.soalab.dtoManipulator.fields.HunterFields;
import volki.soalab.dtoManipulator.fields.TeamFields;
import volki.soalab.dtoManipulator.paramsStringRepresenation.FilterAsString;
import volki.soalab.dto.dragon.ColorDto;
import volki.soalab.dto.dragon.CoordinatesDto;
import volki.soalab.dto.dragon.DragonDtoWithId;
import volki.soalab.exceptions.IllegalFieldException;
import volki.soalab.exceptions.IllegalParamException;
import volki.soalab.exceptions.IllegalValueException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FilterMachine {


    private <T extends Comparable<T>> boolean genericMatcher(T a, T b, String operator) {

        if (a == null || b == null) {
            return a != null;
        }

        return switch (operator) {
            case "eq" -> a.equals(b);
            case "nq" -> !a.equals(b);
            case "gt" -> a.compareTo(b) > 0;
            case "lt" -> a.compareTo(b) < 0;
            case "ge" -> a.compareTo(b) >= 0;
            case "le" -> a.compareTo(b) <= 0;
            default -> throw new IllegalParamException("Unsupported operator: " + operator);
        };
    }


    public boolean dragonMatches(DragonDtoWithId dragonDtoWithId, FilterAsString filterAsString) {
        String field = filterAsString.getField();
        String operator = filterAsString.getOperator();
        String value = filterAsString.getValue();

        DragonFields fieldAsEnum = DragonFields.fromString(field);
        switch (fieldAsEnum) {
            case id:
                Long dragonId = dragonDtoWithId.getId();

                long filterId;
                try {
                    filterId = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new IllegalParamException(
                            String.format("Value (%s) isn't long", value)
                    );
                }

                return genericMatcher(dragonId, filterId, operator);
            case name:
                String dragonName = dragonDtoWithId.getName();
                if (dragonName == null) {
                    return false;
                }

                return genericMatcher(dragonName, value, operator);
            case age:
                Long dragonAge = dragonDtoWithId.getAge();
                if (dragonAge == null) {
                    return false;
                }

                long filterAge;
                try {
                    filterAge = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new IllegalParamException(
                            String.format("Value (%s) isn't long", value)
                    );
                }
                return genericMatcher(dragonAge, filterAge, operator);
            case creationdate:
                LocalDateTime dragonCreationDate = dragonDtoWithId.getCreationDate();
                if (dragonCreationDate == null) {
                    return false;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime filterCreationDate;
                try {
                    filterCreationDate = LocalDateTime.parse(value, formatter);
                } catch (DateTimeParseException e) {
                    throw new IllegalParamException(
                            String.format("Date (%s) doesn't match pattern (yyyy-MM-dd HH:mm)",
                                    value)
                    );
                }
                return genericMatcher(dragonCreationDate, filterCreationDate, operator);
            case coordinates:
                String regex = "\\(\\-?\\d+\\.?\\d*,-?\\d+\\)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(value);
                if (!matcher.matches()) {
                    throw new IllegalParamException(String.format(
                            """
                                    Coordinates should follow (x,y) pattern
                                    Where x is Double type
                                    And y is Long type
                                    You provided (%s)
                                    """, value
                    ));
                }

                String xAsString = matcher.group(1);
                String yAsString = matcher.group(2);

                double filterX;
                try {
                    filterX = Double.parseDouble(xAsString);
                } catch (NumberFormatException ignored) {
                    throw new IllegalParamException(String.format(
                            """
                            Coordinate X should be Double
                            You provided (%s)
                            """, xAsString
                    ));
                }

                long filterY;
                try {
                    filterY = Long.parseLong(yAsString);
                } catch (NumberFormatException e) {
                    throw new IllegalParamException(String.format(
                            """
                            Coordinate Y should be Long
                            You provided (%s)
                            """, yAsString
                    ));
                }


                CoordinatesDto filterCooridnates = new CoordinatesDto(
                        filterX, filterY
                );

                CoordinatesDto dragonCooridantes = dragonDtoWithId.getCoordinates();
                if (dragonCooridantes == null) {
                    return false;
                }

                Double x = dragonCooridantes.getX();

                if (x == null) {
                    return false;
                }

                return genericMatcher(dragonCooridantes, filterCooridnates, operator);

            case wingspan:
                float filterWingspan;
                try {
                    filterWingspan = Float.parseFloat(value);
                } catch (NumberFormatException e) {
                    throw new IllegalParamException(
                            String.format(
                                    """
                                    Wingspan should be Float type
                                    You provided (%s)
                                    """, value)
                    );
                }

                Float dragonWingspan = dragonDtoWithId.getWingspan();
                if (dragonWingspan == null) {
                    return false;
                }

                return genericMatcher(dragonWingspan, filterWingspan, operator);

            case color:
                ColorDto dragonColorDto = dragonDtoWithId.getColor();
                ColorDto filterColorDto = ColorDto.fromString(value);

                if (dragonColorDto == null) {
                    return false;
                }

                return genericMatcher(dragonColorDto, filterColorDto, operator);

            case speaking:
                Boolean dragonIsSpeaking = dragonDtoWithId.getSpeaking();
                boolean filterIsSpeaking = Boolean.parseBoolean(value);

                return genericMatcher(dragonIsSpeaking, filterIsSpeaking, operator);

            case eyescount:
                Long dragonEyesCount = dragonDtoWithId.getHead().getEyesCount();
                long filterEyesCount;
                try {
                    filterEyesCount = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new IllegalParamException(String.format("Eyescount should be long, you provided (%s)", value));
                }

                return genericMatcher(dragonEyesCount, filterEyesCount, operator);
        }

        throw new IllegalParamException(String.format(
                "Field (%s) doesn't exist", field
        ));
    }

    public boolean hunterMatches(HunterDtoWithId hunterDtoWithId, FilterAsString filterAsString) {
        String field = filterAsString.getField();
        String operator = filterAsString.getOperator();
        String value = filterAsString.getValue();

        HunterFields hunterFields = HunterFields.fromString(field);
        switch (hunterFields) {
            case id: {
                Long hunterId = hunterDtoWithId.getId();
                long filterId;

                try {
                    filterId = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new IllegalParamException(String.format(
                            "Value (%s) should be Long", value
                    ));
                }

                if (hunterId == null) {
                    return false;
                }

                return genericMatcher(hunterId, filterId, operator);
            }
            case firstName: {
                String hunterFirstName = hunterDtoWithId.getFirstName();
                return genericMatcher(hunterFirstName, value, operator);
            }
            case lastName: {
                String hunterLastName = hunterDtoWithId.getLastName();
                return genericMatcher(hunterLastName, value, operator);
            }
            case strength: {
                Integer hunterStrength = hunterDtoWithId.getStrength();
                int filterStrength;
                try {
                    filterStrength = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new IllegalParamException(String.format(
                            "Value (%s) should be Integer", value
                    ));
                }
                return genericMatcher(hunterStrength, filterStrength, operator);
            }
            case teamId: {
                Long teamId = hunterDtoWithId.getTeamId();
                long filterTeamId;
                try {
                    filterTeamId = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new IllegalParamException(String.format(
                            "Value (%s) should be long", value
                    ));
                }
                return genericMatcher(teamId, filterTeamId, operator);
            }
            default: throw new IllegalParamException(String.format("Field (%s) doesn't exist", field));
        }
    }

    public boolean teamMatches(TeamDtoWithId teamDtoWithId, FilterAsString filterAsString) {
        String field = filterAsString.getField();
        String operator = filterAsString.getOperator();
        String value = filterAsString.getValue();

        TeamFields teamFields = TeamFields.valueOf(field);
        switch (teamFields) {
            case id: {
                Long teamId = teamDtoWithId.getId();
                long filterTeamId;
                try {
                    filterTeamId = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new IllegalValueException(field, value, Long.class);
                }
                return genericMatcher(teamId, filterTeamId, operator);
            }
            case name: {
                String teamName = teamDtoWithId.getName();
                return genericMatcher(teamName, value, operator);
            }
            case power:
                Integer teamPower = teamDtoWithId.getPower();
                int filterTeamPower;
                try {
                    filterTeamPower = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new IllegalValueException(field, value, Integer.class);
                }
                return genericMatcher(teamPower, filterTeamPower, operator);
            default: throw new IllegalFieldException(field);
        }
    }

}
