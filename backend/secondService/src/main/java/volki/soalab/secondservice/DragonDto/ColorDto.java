package volki.soalab.secondservice.DragonDto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
@XmlAccessorType(XmlAccessType.FIELD)
public enum ColorDto {
    @XmlEnumValue("GREEN") GREEN,
    @XmlEnumValue("BLUE") BLUE,
    @XmlEnumValue("ORANGE") ORANGE,
    @XmlEnumValue("WHITE") WHITE,
    @XmlEnumValue("BROWN") BROWN

}
