package secondservice.businesslogic.ejb;

import jakarta.ejb.Remote;
import secondservice.businesslogic.exceptions.EjbException;

@Remote
public interface MoveToCaveRemote {
    boolean moveToCave(String baseUrl, int teamId, int caveId) throws Exception;
}
