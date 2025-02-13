package volki.soalab.secondservice.DungeonDto;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "dungeonList")
@XmlAccessorType(XmlAccessType.FIELD)
public class DungeonDtoList {
    @XmlElement(name = "dungeon")
    private List<DungeonDto> dungeonDtoList;

    public DungeonDto toFind(boolean order) {
        DungeonDto result = null;
        for (DungeonDto dungeonDto: dungeonDtoList) {
            if (dungeonDto.getDragonId() == null) {
                continue;
            }
            if (result == null) {
                result = dungeonDto;
                continue;
            }
            if (order && result.getSize() < dungeonDto.getSize()) {
                result = dungeonDto;
                continue;
            }
            if (!order && result.getSize() > dungeonDto.getSize()) {
                result = dungeonDto;
            }
        }
        return result;
    }


}
