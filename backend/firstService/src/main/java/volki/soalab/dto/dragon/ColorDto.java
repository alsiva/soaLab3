package volki.soalab.dto.dragon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import volki.soalab.entities.dragon.ColorEntity;
import volki.soalab.exceptions.IllegalParamException;

public enum ColorDto {
    @JacksonXmlProperty(localName = "GREEN") GREEN,
    @JacksonXmlProperty(localName = "BLUE") BLUE,
    @JacksonXmlProperty(localName = "ORANGE") ORANGE,
    @JacksonXmlProperty(localName = "WHITE") WHITE,
    @JacksonXmlProperty(localName = "BROWN") BROWN;



    public static ColorDto fromEntity(ColorEntity colorEntity) {
        if (colorEntity == null || colorEntity.getName() == null) {
            throw new IllegalArgumentException("Color entity or name is null");
        }
        return ColorDto.valueOf(colorEntity.getName().toUpperCase());
    }

    @JsonCreator
    public static ColorDto fromString(String colorString) {
        colorString = colorString.toUpperCase();
        ColorDto colorDto;
        try {
            colorDto = ColorDto.valueOf(colorString);
        } catch (IllegalArgumentException e) {
            throw new IllegalParamException(
                    String.format("Color (%s) doesn't exist", colorString)
            );
        }
        return colorDto;
    }
}
