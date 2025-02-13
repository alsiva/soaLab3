package volki.soalab;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JacksonXmlRootElement(localName = "errorResponse")
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @JacksonXmlProperty(localName = "error")
    private String error;
    @JacksonXmlProperty(localName = "details")
    private String details;
}
