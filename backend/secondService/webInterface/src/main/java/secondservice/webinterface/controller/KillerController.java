package secondservice.webinterface.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import secondservice.businesslogic.DragonDto.DragonDto;
import secondservice.businesslogic.ejb.FindByCaveDepthRemote;
import secondservice.businesslogic.ejb.MoveToCaveRemote;

import javax.naming.InitialContext;


@Path("/killer")
public class KillerController {

    private final String url = "https://localhost:8081";

    @GET
    @Path("/pelmeni")
    public Response positive() {
        return Response.ok().build();
    }

    @GET
    @Path("/dragon/find-by-cave-depth/{max-of-min}")
    public Response findByCaveDepth(@PathParam("max-of-min") String maxOrMin) {
        boolean max = maxOrMin.equalsIgnoreCase("max");

        try {
            InitialContext ctx = new InitialContext();
            FindByCaveDepthRemote finder = (FindByCaveDepthRemote) ctx.lookup(
                    "java:global/secondService/FindByCaveDepthEJB!secondservice.businesslogic.ejb.FindByCaveDepthRemote"
            );
            DragonDto dragonDto = finder.getDragon(url, max);
            System.out.println(dragonDto);
            return Response.ok(dragonDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("Error during request processing: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response yes() {
        return Response.ok().build();
    }

    @GET
    @Path("/team/{team-id}/move-to-cave/{cave-id}")
    public Response moveToCave(@PathParam("team-id") int teamId,
                               @PathParam("cave-id") int caveId) {
        try{
            InitialContext ctx = new InitialContext();
            MoveToCaveRemote mover = (MoveToCaveRemote) ctx.lookup(
                    "java:global/businessLogic/MoveToCaveEJB!secondservice.businesslogic.ejb.MoveToCaveRemote"
            );
            boolean res = mover.moveToCave(url, teamId, caveId);
            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("Error during request processing: " + e.getMessage())
                    .build();
        }

    }
}
