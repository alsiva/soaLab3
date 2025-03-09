package secondservice.businesslogic.DragonDto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "head") // JAXB эквивалент @JacksonXmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DragonHeadDto  implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "eyesCount") // JAXB эквивалент @JacksonXmlProperty
    private Long eyesCount;
}
