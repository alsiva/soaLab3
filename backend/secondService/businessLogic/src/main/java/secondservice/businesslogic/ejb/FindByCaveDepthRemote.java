package secondservice.businesslogic.ejb;

import jakarta.ejb.Remote;
import secondservice.businesslogic.DragonDto.DragonDto;

@Remote
public interface FindByCaveDepthRemote {
    DragonDto getDragon(String baseUrl, boolean max) throws Exception;
}
