package secondservice.businesslogic.ejb.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import secondservice.businesslogic.DragonDto.DragonDto;
import secondservice.businesslogic.config.CustomClient;
import secondservice.businesslogic.ejb.FindByCaveDepthRemote;
import secondservice.businesslogic.DungeonDto.DungeonDto;
import secondservice.businesslogic.DungeonDto.DungeonDtoList;
import secondservice.businesslogic.ejb.HTTPClientLocal;

@Stateless(name = "FindByCaveDepthEJB")
@Remote(FindByCaveDepthRemote.class)
public class FindByCaveDepth implements FindByCaveDepthRemote {
    @EJB
    HTTPClientLocal httpClient;
    @Override
    public DragonDto getDragon(String baseUrl, boolean max) throws Exception {
        String urlDungeons = baseUrl + "/dungeons";
        String urlDragon = baseUrl + "/dragons/";
        Long dragonId = null;
        try(Response dungeonsResponse = httpClient.getRequest(urlDungeons)) {
            if (dungeonsResponse.getStatus() == 200) {
                DungeonDtoList responseBody = dungeonsResponse.readEntity(DungeonDtoList.class);
                if (responseBody.getDungeonDtoList().isEmpty()) {
                    throw new Exception("Dungeon not found");
                }
                DungeonDto toFind = responseBody.toFind(max);
                if (toFind == null) {
                    throw new Exception("Dragon not found");
                }
                dragonId = toFind.getDragonId();
            } else {
                System.out.println("Error: Response code " + dungeonsResponse.getStatus());
                // Получаем тело ответа, если оно есть (например, текст ошибки)
                String errorMessage = dungeonsResponse.readEntity(String.class);
                System.out.println("Error message: " + errorMessage);
                // Возвращаем ответ с ошибкой и подробной информацией
                throw new Exception("Error: " + dungeonsResponse.getStatus() + ", Message: " + errorMessage);
            }
        }
        urlDragon = urlDragon + dragonId;
        try(Response dragonResponse = httpClient.getRequest(urlDragon)) {
            System.out.println("Response code " + dragonResponse.getStatus());
            if (dragonResponse.getStatus() == 200) {
                // Обработка ответа от сервера дракона
                return dragonResponse.readEntity(DragonDto.class);
            } else {
                throw new Exception("Error fetching dragon details: " + dragonResponse.getStatus());
            }
        }

    }
}
