package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;

import java.util.List;

@Path("maisons")
@Log
public class MaisonResource {

    private final MaisonDAO maisonDAO = new MaisonDAO();

    @GET
    @Path("qp")
    public String testqp(@QueryParam("minAge") int minAge,
                         @QueryParam("maxAge") int maxAge) {
        return "hello minAge=" + minAge + " maxAge=" + maxAge;
    }

    @GET
    @Path("pp/{id}")
    public String testpp(@PathParam("id") int id) {
        return "hello id=" + id;
    }

    @GET
    @Path("hp")
    public String testhp(@HeaderParam("X-MY_PARAM") String param) {
        return "hello header param=" + param;
    }

    @GET
    @Path("json")
    @Produces({MediaType.APPLICATION_JSON})
    public Maison testJson() {
        return Maison.builder().id(12).name("M_12").build();
    }

    /**
     * Returns all the Maison
     *
     * @return a List of Maison
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Maison> findAll() {
        return maisonDAO.findAll();
    }

    /**
     * Returns all the Maison
     *
     * @return a List of Maison
     */
    @GET
    @Path("insalubres")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Maison> findAllInsalubre() {
        return maisonDAO.findAllByVetuste(Maison.Vetuste.INSALUBRE);
    }

    /**
     * Returns a Maison by id
     *
     * @param id the id of the Maison
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id:[0-9]+}")
    public Response find(@PathParam("id") long id) {
        Maison entity = maisonDAO.find(id);
        if (entity != null) return Response.ok(entity).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Persists a Maison given in json
     * curl -X PUT -H "Content-Type: application/json" -d '{"surface":90,"name":"XYZ"}' http://localhost:9998/minijparest/maisons
     *
     * @param maison
     * @return the Maison with the id.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Maison postMaison(Maison maison) {
        log.info("-PERSISTING-->" + maison);
        return maisonDAO.persist(maison);
    }

}
