package volki.soalab.secondservice.DragonDto;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DragonDateAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
