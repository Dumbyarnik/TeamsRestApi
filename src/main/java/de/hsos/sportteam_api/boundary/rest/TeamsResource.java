package de.hsos.sportteam_api.boundary.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.PathParam;

import de.hsos.sportteam_api.control.Verwaltung;
import de.hsos.sportteam_api.models.person.PersonDTO;
import de.hsos.sportteam_api.models.person.PersonPostDTO;
import de.hsos.sportteam_api.models.person.PersonWithAttributesDTO;
import de.hsos.sportteam_api.models.team.TeamDTO;
import de.hsos.sportteam_api.models.team.TeamManagerRelationshipDTO;
import de.hsos.sportteam_api.models.team.TeamPlayersRelationshipDTO;
import de.hsos.sportteam_api.models.team.TeamPostDTO;

@ApplicationScoped
@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamsResource {

    // NO private member here!
    @Inject
    Verwaltung teamVerwaltung;

    @Context
    UriInfo uriInfo;

    // http://localhost:8080/teams
    @GET
    public Response getTeams() {
        List<TeamDTO> teamsDTO = this.teamVerwaltung.getTeams();
        List<Link> links = new ArrayList<>();

        if (teamsDTO.isEmpty()) {
            return Response.status(Status.NOT_FOUND).entity("Keine Teams").build();
        }

        // how to build links
        // https://dennis-xlc.gitbooks.io/restful-java-with-jax-rs-2-0-2rd-edition/content/en/part1/chapter10/building_links_and_link_headers.html
        for (TeamDTO tmp : teamsDTO) {
            // absolute path - the path of this get moethod
            URI uri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path(String.valueOf(tmp.id)).build();
            Link link = Link.fromUri(uri).rel("teams").type(MediaType.APPLICATION_JSON).param("method", "GET").build();
            links.add(link);
        }

        // .links() - putting them in a header
        return Response.ok(teamsDTO).links(links.toArray(new Link[links.size()])).build();
    }
    
    // http://localhost:8080/teams/1?include=manager,players}
    @GET
    @Path("/{id}")
    public Response getTeam(@PathParam("id") Long id, @QueryParam("include") String include) {

        TeamDTO team = null;
        List<Link> links = new ArrayList<>();

        if (include != null) {
            // only manager
            if (include.contains("manager") && !include.contains("players")) {
                team = this.teamVerwaltung.getTeamManagerInclude(id);
                links.addAll(this.getLinks("manager"));
            } 
            // only players
            else if (include.contains("players") && !include.contains("manager")) {
                team = this.teamVerwaltung.getTeamPlayersInclude(id);
                links.addAll(this.getLinks("players"));
            } 
            // manager & players
            else if (include.contains("players") && include.contains("manager")) {
                team = this.teamVerwaltung.getTeamAllInclude(id);
                links.addAll(this.getLinks("manager"));
                links.addAll(this.getLinks("players"));
            }
            // no one
            else {
                team = this.teamVerwaltung.getTeam(id);
            }
        } else {
            team = this.teamVerwaltung.getTeam(id);
        }

        if (team != null) {
            return Response.ok(team).links(links.toArray(new Link[links.size()])).build();
        }

        return Response.status(Status.NOT_FOUND).entity("Team " + id + " existiert nicht").build();
    }

    // http://localhost:8080/teams
    @POST
    public Response addTeam(TeamPostDTO team) {
        TeamDTO newTeam = this.teamVerwaltung.addTeam(team);

        if (newTeam != null) {
            URI uri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path(String.valueOf(newTeam.id)).build();
            Link link = Link.fromUri(uri).rel("teams").type(MediaType.APPLICATION_JSON).param("method", "GET").build();
            return Response.status(Status.CREATED).entity(newTeam).links(link).build();
        }
        
        return Response.status(Status.CONFLICT).entity("Team mit diesem id existiert").build();
    }

    // http://localhost:8080/teams
    @PUT
    public Response updateTeam(TeamPostDTO team) {
        TeamDTO newTeam = this.teamVerwaltung.updateTeam(team);

        if (newTeam != null) {
            URI selfUri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path(String.valueOf(newTeam.id)).build();
            Link selfLink = Link.fromUri(selfUri).rel("teams").type(MediaType.APPLICATION_JSON).param("method", "GET").build();
            return Response.ok(newTeam).links(selfLink).build();
        }

        return Response.status(Status.NOT_FOUND).entity("Team existiert nicht").build();
    }

    // http://localhost:8080/teams
    @DELETE
    @Path("/{id}")
    public Response deleteTeam(@PathParam("id") Long id) {

        if (this.teamVerwaltung.deleteTeam(id)) {
            return Response.ok().build();
        }

        return Response.status(Status.NOT_FOUND).entity("Team " + id + " existiert nicht").build();
    }

    // http://localhost:8080/teams/0/relationships/players
    @GET
    @Path("/{id}/relationships/players")
    public Response getPlayersRelation(@PathParam("id") Long teamId) {
        TeamPlayersRelationshipDTO dto = 
            this.teamVerwaltung.getTeamPlayersRelationshipDTO(teamId);

        if (dto != null) {
            List<Link> links = this.getRelationshipLinks();
            return Response.ok(dto).links(links.toArray(new Link[links.size()])).build();
        }

        return Response.status(Status.NOT_FOUND).entity("Team mit Id " + teamId + " existiert nicht.").build();
    }

    // http://localhost:8080/teams/0/players
    @GET
    @Path("/{id}/players")
    public Response getPlayers(@PathParam("id") Long teamId) {
        List<PersonWithAttributesDTO> players = this.teamVerwaltung.getPlayers(teamId);

        if (players.isEmpty()) {
            return Response.status(Status.NOT_FOUND)
                .entity("Team " + teamId + " existiert nicht").build();
        }

        URI uri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).build();
        Link link = Link.fromUri(uri).rel("persons").type(MediaType.APPLICATION_JSON).param("method", "GET").build();
        return Response.ok(players).links(link).build();
    }

    // http://localhost:8080/teams/0/relationships/manager
    @GET
    @Path("/{id}/relationships/manager")
    public Response getManagerRelation(@PathParam("id") Long teamId) {
        TeamManagerRelationshipDTO dto = teamVerwaltung.getTeamManagerRelationshipDTO(teamId);

        if (dto != null) {
            List<Link> links = this.getRelationshipLinks();
            return Response.ok(dto)
                .links(links.toArray(new Link[links.size()])).build();
        }

        return Response.status(Status.NOT_FOUND)
            .entity("Team " + teamId + " existiert nicht").build();
    }

    // http://localhost:8080/teams/0/manager
    @GET
    @Path("/{id}/manager")
    public Response getManager(@PathParam("id") Long teamId) {
        PersonWithAttributesDTO dto = this.teamVerwaltung.getManager(teamId);

        if (dto != null) {
            URI uri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).build();
            Link link = Link.fromUri(uri).rel("persons").type(MediaType.APPLICATION_JSON).param("method", "GET").build();
            return Response.ok(dto).links(link).build();
        }

        return Response.status(Status.NOT_FOUND)
            .entity("Team " + teamId + " existiert nicht").build();
    }

    
    @POST
    @Path("/{id}/players")
    public Response addPlayer(@PathParam("id") Long teamId, PersonPostDTO player) {
        PersonDTO newPlayer = this.teamVerwaltung.addPlayer(teamId, player);

        if (newPlayer != null) {
            URI uri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).build();
            Link link = Link.fromUri(uri).rel("persons")
                .type(MediaType.APPLICATION_JSON).param("method", "GET").build();
            return Response.status(Status.CREATED).entity(newPlayer).links(link).build();
        }

        return Response.status(Status.CONFLICT)
            .entity("Spieler existiert schon im Team " + teamId).build();
    }

    // http://localhost:8080/teams/0/players/1
    @DELETE
    @Path("/{teamId}/players/{playerId}")
    public Response deletePlayer(@PathParam("teamId") Long teamId, 
            @PathParam("playerId") Long playerId) {
        if (this.teamVerwaltung.deletePlayer(teamId, playerId)) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_FOUND)
            .entity("Player " + playerId + 
            " existiert nicht im Team " + teamId).build();
    }

    private List<Link> getLinks(String path){
        return Arrays.asList(this.getSelfLink(path), this.getRelationshipLink(path));
    }

    private Link getSelfLink(String path){
        URI selfUri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path(path).build();
        return Link.fromUri(selfUri).rel("persons").type(MediaType.APPLICATION_JSON).param("method", "GET").build();
        
    }

    private Link getRelationshipLink(String path){
        URI relatedUri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path("relationships").path(path).build();
        return Link.fromUri(relatedUri).rel("persons").type(MediaType.APPLICATION_JSON).param("method", "GET").build();
    }

    private List<Link> getRelationshipLinks(){
        URI uri = UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).build();
        Link link = Link.fromUri(uri).rel("persons")
            .type(MediaType.APPLICATION_JSON).param("method", "GET").build();
        UriBuilder uribuilder = UriBuilder.fromUri(this.uriInfo.getBaseUri());

        for (PathSegment tmp : uriInfo.getPathSegments()) {
            if(!tmp.getPath().equals("relationships")){
                uribuilder.path(tmp.getPath());
            }
        }

        Link relatedLink = Link.fromUri(uribuilder.build())
            .rel("persons").type(MediaType.APPLICATION_JSON)
            .param("method", "GET").build();
        return Arrays.asList(link, relatedLink);
    }
}
