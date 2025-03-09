package secondservice.businesslogic.ejb.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import secondservice.businesslogic.ejb.HTTPClientLocal;
import secondservice.businesslogic.ejb.MoveToCaveRemote;
import secondservice.businesslogic.exceptions.EjbException;
import secondservice.businesslogic.DungeonDto.DungeonDto;

@Stateless(name = "MoveToCaveEJB")
@Remote(MoveToCaveRemote.class)
public class MoveToCave implements MoveToCaveRemote {
    @EJB
    HTTPClientLocal httpClient;

    @Override
    public boolean moveToCave(String baseUrl, int teamId, int caveId) throws Exception {
        String teamsUrl = baseUrl + "/teams/" + teamId;
        Response teamsResponse = httpClient.getRequest(teamsUrl);
        if (teamsResponse.getStatus() != 200) {
            System.out.println("Error: Response code " + teamsResponse.getStatus());

            // Получаем тело ответа, если оно есть (например, текст ошибки)
            String errorMessage = teamsResponse.readEntity(String.class);
            System.out.println("Error message: " + errorMessage);

            // Возвращаем ответ с ошибкой и подробной информацией
            throw new EjbException(teamsResponse.getStatus(), "Error: " + teamsResponse.getStatus() + ", Message: " + errorMessage);
        }

        String dungeonUrl = baseUrl + "/dungeons/" + caveId;
        Response dungeonsResponse = httpClient.getRequest(dungeonUrl);
        if (dungeonsResponse.getStatus() != 200) {
            throw new EjbException(dungeonsResponse.getStatus(), "Error fetching dungeon details: " + dungeonsResponse.getStatus());
        }
        Long dragonId = dungeonsResponse.readEntity(DungeonDto.class).getDragonId();
        try (Response dragonResponse = httpClient.deleteRequest(baseUrl + "/dragons/" + dragonId)) {
            if (dragonResponse.getStatus() != 200) {
                throw new EjbException(dragonResponse.getStatus(), "Error deleting dragon details: " + dragonResponse.getStatus());
            }
        }
        return true;
    }
}
