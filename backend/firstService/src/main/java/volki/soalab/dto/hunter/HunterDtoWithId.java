package volki.soalab.dto.hunter;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import volki.soalab.entities.hunter.HunterEntity;


@Getter
@JacksonXmlRootElement(localName = "hunter") // Указываем корневой элемент
@JsonPropertyOrder({"id"})
@AllArgsConstructor
@NoArgsConstructor
public class HunterDtoWithId extends HunterDto {

    @JacksonXmlProperty(localName = "id")
    @NotNull
    //@NotEmpty
    @Setter
    private Long id;
    public HunterDtoWithId(HunterEntity hunterEntity) {
        super(hunterEntity);
        this.id = hunterEntity.getId();
    }
}
