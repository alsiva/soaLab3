package volki.soalab.dto.dragon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JacksonXmlRootElement(localName = "error")
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    @JacksonXmlProperty(localName = "message")
    private String message;
}
