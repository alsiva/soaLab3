package secondservice.businesslogic.ejb.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import secondservice.businesslogic.config.CustomClient;
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
        try(Client client = new CustomClient().createClient()){
            WebTarget target = client.target(teamsUrl);
            Response teamsResponse = target.request().get();
            if (teamsResponse.getStatus() != 200) {
                System.out.println("Error: Response code " + teamsResponse.getStatus());

                // Получаем тело ответа, если оно есть (например, текст ошибки)
                String errorMessage = teamsResponse.readEntity(String.class);
                System.out.println("Error message: " + errorMessage);

                // Возвращаем ответ с ошибкой и подробной информацией
                throw new EjbException(teamsResponse.getStatus(), "Error: " + teamsResponse.getStatus() + ", Message: " + errorMessage);
            }
        }
        String dungeonUrl = baseUrl + "/dungeons/" + caveId;
        try(Client client = new CustomClient().createClient()){
            WebTarget target = client.target(dungeonUrl);
            Response dungeonsResponse = target.request().get();
            if (dungeonsResponse.getStatus() != 200) {
                throw new EjbException(dungeonsResponse.getStatus(), "Error fetching dungeon details: " + dungeonsResponse.getStatus());
            }
            Long dragonId = dungeonsResponse.readEntity(DungeonDto.class).getDragonId();
            target = client.target(baseUrl + "/dragons/" + dragonId);
            Response dragonResponse = target.request().delete();
            if (dragonResponse.getStatus() != 200) {
                throw new EjbException(dragonResponse.getStatus(), "Error deleting dragon details: " + dragonResponse.getStatus());
            }

        }
        return true;
    }
}
