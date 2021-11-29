package de.hsos.sportteam_api.control;

import de.hsos.sportteam_api.entities.Person;
import de.hsos.sportteam_api.entities.Team;
import de.hsos.sportteam_api.models.person.ManagerRelationshipDTO;
import de.hsos.sportteam_api.models.person.ManagerUndPlayerRelationshipDTO;
import de.hsos.sportteam_api.models.person.PersonAttributesDTO;
import de.hsos.sportteam_api.models.person.PersonDTO;
import de.hsos.sportteam_api.models.person.PersonWithAttributesDTO;
import de.hsos.sportteam_api.models.person.PlayersRelationshipDTO;
import de.hsos.sportteam_api.models.team.TeamAllIncludeDTO;
import de.hsos.sportteam_api.models.team.TeamAllRelationshipsDTO;
import de.hsos.sportteam_api.models.team.TeamAttributesDTO;
import de.hsos.sportteam_api.models.team.TeamDTO;
import de.hsos.sportteam_api.models.team.TeamManagerIncludeDTO;
import de.hsos.sportteam_api.models.team.TeamManagerRelationshipDTO;
import de.hsos.sportteam_api.models.team.TeamPlayersIncludeDTO;
import de.hsos.sportteam_api.models.team.TeamPlayersRelationshipDTO;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Konverter {

    public TeamDTO convertTeamToDTOTeam(Team team) {
        if (team != null) {
            return new TeamDTO(team.getId(), this.createTeamAttributes(team));
        }
        return null;
    }

    public TeamManagerIncludeDTO convertTeamToManagerInclude(Team team) {
        if (team != null) {
            List<Person> managers = new ArrayList<>();
            managers.add(team.getManager());
            return new TeamManagerIncludeDTO(team.getId(), 
                this.createTeamAttributes(team), 
                this.createManagerRelationships(team), 
                this.convertPersonWithAttributes(managers));
        }
        return null;
    }

    // gives team attributes - name & category
    private TeamAttributesDTO createTeamAttributes(Team team){
        return new TeamAttributesDTO(team.getName(), team.getCategory());
    }

    // gives team a manager 
    private ManagerRelationshipDTO createManagerRelationships(Team team){
        return new ManagerRelationshipDTO(new PersonDTO(team.getManager().getId()));
    }

    // converts persons to List of PersonWithAttributesDTO 
    private List<PersonWithAttributesDTO> convertPersonWithAttributes(List<Person> persons) {
        if (!persons.isEmpty()) {
            List<PersonWithAttributesDTO> personWithAttributesDTOs = new ArrayList<>();
            //persons.forEach(person -> personWithAttributesDTOs.add(this.convertPerson(person)));
            for (Person tmp : persons) {
                personWithAttributesDTOs.add(this.convertPerson(tmp));
            }
            return personWithAttributesDTOs;
        }
        return new ArrayList<>();
    }

    // converts person to PersonWithAttributesDTO 
    public PersonWithAttributesDTO convertPerson(Person person) {
        if (person != null) {
            return new PersonWithAttributesDTO(person.getId(), new PersonAttributesDTO(person.getName()));
        }
        return null;
    }

    public TeamPlayersIncludeDTO convertTeamToPlayersInclude(Team team) {
        if (team != null) {
            return new TeamPlayersIncludeDTO(team.getId(), 
                this.createTeamAttributes(team), 
                this.createPlayersRelationship(team), 
                this.convertPersonWithAttributes(team.getPlayers()));
        }
        return null;
    }

    public TeamAllIncludeDTO convertTeamAllInclude(Team team) {
        if (team != null) {
            List<Person> persons = team.getPlayers();
            persons.add(team.getManager());
            return new TeamAllIncludeDTO(team.getId(), 
                this.createTeamAttributes(team), 
                this.createManagerUndPlayerRelationship(team), 
                this.convertPersonWithAttributes(persons));
        }
        return null;
    }

    // full relationships - manager + players
    private ManagerUndPlayerRelationshipDTO createManagerUndPlayerRelationship(Team team){
        List<PersonDTO> players = new ArrayList<>();

        for (Person tmp : team.getPlayers()) {
            players.add(new PersonDTO(tmp.getId()));
        }
        
        return new ManagerUndPlayerRelationshipDTO(players, new PersonDTO(team.getManager().getId()));
    }

    // gives back team with players
    public TeamPlayersRelationshipDTO convertTeamToPlayersRelationshipDTO(Team team) {    
        if(team != null){
            return new TeamPlayersRelationshipDTO(team.getId(), 
                this.createTeamAttributes(team), 
                this.createPlayersRelationship(team));
        } 
        return null;
    }

    private PlayersRelationshipDTO createPlayersRelationship(Team team){
        List<PersonDTO> players = new ArrayList<>();

        for (Person tmp : team.getPlayers()) {
            players.add(new PersonDTO(tmp.getId()));
        }

        return new PlayersRelationshipDTO(players);
    }

    // gives back team with manager
    public TeamManagerRelationshipDTO convertTeamToManagerRelationship(Team team) {
        if(team != null){
            return new TeamManagerRelationshipDTO(team.getId(), 
                this.createTeamAttributes(team), 
                this.createManagerRelationships(team));
        }

        return null;
    }





    

    

    public TeamAllRelationshipsDTO convertTeamAllRel(Team team) {
        if(team != null){
            return new TeamAllRelationshipsDTO(team.getId(), this.createTeamAttributes(team), this.createManagerUndPlayerRelationship(team));
        }
        return null;
    }

    

    

    

    

    

    

    

    

    
}