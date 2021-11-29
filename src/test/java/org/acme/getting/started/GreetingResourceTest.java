package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import de.hsos.sportteam_api.entities.TeamKategorie;
import de.hsos.sportteam_api.models.person.PersonPostDTO;
import de.hsos.sportteam_api.models.team.TeamAttributesDTO;
import de.hsos.sportteam_api.models.team.TeamDTO;
import de.hsos.sportteam_api.models.team.TeamPostDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.core.MediaType;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testAddTeam() {

        TeamPostDTO teamPost = new TeamPostDTO(Long.valueOf(22), "Test", TeamKategorie.SENIORS, 
            new PersonPostDTO(Long.valueOf(11), "Trainer Bernd"),
            new ArrayList<>(Arrays.asList(new PersonPostDTO(Long.valueOf(101), 
                "Max Mustermann"))));
        
        TeamDTO teamResponse = new TeamDTO(Long.valueOf(22), 
            new TeamAttributesDTO("Test", TeamKategorie.SENIORS));
                
        given()
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .body(teamPost)
        .when()
            .post("/teams")
        .then()
            .statusCode(201)
            .body("id", is(22),
                "type", is(teamResponse.type),
                "attributes.name", is(teamResponse.attributes.name),
                "attributes.category", is("SENIORS"));
    }

    @Test
    public void testUpdateTeam() {

        TeamPostDTO teamPost = new TeamPostDTO(Long.valueOf(22), "Test", TeamKategorie.JUNIORS, 
            new PersonPostDTO(Long.valueOf(11), "Trainer Bernd"),
            new ArrayList<>(Arrays.asList(new PersonPostDTO(Long.valueOf(101), 
                "Max Mustermann"))));
        
        TeamDTO teamResponse = new TeamDTO(Long.valueOf(22), 
            new TeamAttributesDTO("Test", TeamKategorie.JUNIORS));
            
        given()
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .body(teamPost)
        .when()
            .put("/teams")
        .then()
            .statusCode(200)
            .body("id", is(22),
                "type", is(teamResponse.type),
                "attributes.name", is(teamResponse.attributes.name),
                "attributes.category", is("JUNIORS"));
    }

    @Test
    public void testDeleteTeam() {                
        given()
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .pathParam("id", 22)
        .when()
            .delete("/teams/{id}")
        .then()
            .statusCode(200);
    }

}