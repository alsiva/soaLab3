package secondservice.businesslogic.DragonDto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

import java.io.Serializable;

@XmlEnum
@XmlAccessorType(XmlAccessType.FIELD)
public enum ColorDto{
    @XmlEnumValue("GREEN") GREEN,
    @XmlEnumValue("BLUE") BLUE,
    @XmlEnumValue("ORANGE") ORANGE,
    @XmlEnumValue("WHITE") WHITE,
    @XmlEnumValue("BROWN") BROWN

}
