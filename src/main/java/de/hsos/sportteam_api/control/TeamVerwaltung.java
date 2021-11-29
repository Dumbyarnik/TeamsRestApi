package de.hsos.sportteam_api.control;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.hsos.sportteam_api.acl.Passwesen;
import de.hsos.sportteam_api.entities.Person;
import de.hsos.sportteam_api.entities.Team;
import de.hsos.sportteam_api.entities.TeamsKatalog;
import de.hsos.sportteam_api.models.person.PersonPostDTO;
import de.hsos.sportteam_api.models.person.PersonWithAttributesDTO;
import de.hsos.sportteam_api.models.team.TeamAllIncludeDTO;
import de.hsos.sportteam_api.models.team.TeamAllRelationshipsDTO;
import de.hsos.sportteam_api.models.team.TeamDTO;
import de.hsos.sportteam_api.models.team.TeamManagerIncludeDTO;
import de.hsos.sportteam_api.models.team.TeamManagerRelationshipDTO;
import de.hsos.sportteam_api.models.team.TeamPlayersIncludeDTO;
import de.hsos.sportteam_api.models.team.TeamPlayersRelationshipDTO;
import de.hsos.sportteam_api.models.team.TeamPostDTO;

@Singleton
public class TeamVerwaltung implements Verwaltung {

    @Inject
    @SpielerpassQualifier(type = SpielerpassType.ATOMIC)
    Passwesen passwesen;

    @Inject
    TeamsKatalog teamKatalog;
    @Inject
    Konverter konverter;

    @Override
    public List<TeamDTO> getTeams() {
        List<TeamDTO> teamDTO = new ArrayList<>();
        List<Team> teams = this.teamKatalog.getTeams();

        for (Team tmp : teams) {
            teamDTO.add(this.konverter.convertTeamToDTOTeam(tmp));                    
        }
        
        return teamDTO;
    }
    
    @Override
    public TeamDTO getTeam(Long id) {
        return this.konverter.convertTeamToDTOTeam(this.teamKatalog.getTeam(id));
    }

    @Override
    public TeamManagerIncludeDTO getTeamManagerInclude(Long id) {
        return this.konverter.convertTeamToManagerInclude(this.teamKatalog.getTeam(id));
    }

    @Override
    public TeamPlayersIncludeDTO getTeamPlayersInclude(Long id) {
        return this.konverter.convertTeamToPlayersInclude(this.teamKatalog.getTeam(id));
    }

    @Override
    public TeamAllIncludeDTO getTeamAllInclude(Long id) {
        return this.konverter.convertTeamAllInclude(this.teamKatalog.getTeam(id));
    }

    @Override
    public TeamDTO addTeam(TeamPostDTO team) {
        return this.konverter.convertTeamToDTOTeam(this.teamKatalog.addTeam(team));
    }

    @Override
    public TeamDTO updateTeam(TeamPostDTO team) {
        return this.konverter.convertTeamToDTOTeam(this.teamKatalog.updateTeam(team));
    }

    @Override
    public boolean deleteTeam(Long id) {
        return this.teamKatalog.deleteTeam(id);
    }

    @Override
    public TeamPlayersRelationshipDTO getTeamPlayersRelationshipDTO(Long id) {
        return this.konverter.convertTeamToPlayersRelationshipDTO(this.teamKatalog.getTeam(id));
    }

    @Override
    public List<PersonWithAttributesDTO> getPlayers(Long teamId) {
        List<PersonWithAttributesDTO> players = new ArrayList<>();
        Team team = this.teamKatalog.getTeam(teamId);

        if(team != null){
            for (Person tmp : team.getPlayers()) {
                players.add(this.konverter.convertPerson(tmp));
            }
        }

        return players;
    }

    @Override
    public TeamManagerRelationshipDTO getTeamManagerRelationshipDTO(Long id) {
        return this.konverter.convertTeamToManagerRelationship(this.teamKatalog.getTeam(id));
    }

    @Override
    public PersonWithAttributesDTO getManager(Long teamId) {
        Team team = this.teamKatalog.getTeam(teamId);
        if(team != null){
            return this.konverter.convertPerson(team.getManager());
        }
        return null;
    }

    @Override
    public PersonWithAttributesDTO addPlayer(Long teamId, PersonPostDTO player) {
        final Long playerID = this.passwesen.getPlayerId();
        player.id = playerID;
        return this.konverter.convertPerson(this.teamKatalog.addPlayer(teamId, player));
    }

    @Override
    public boolean deletePlayer(Long teamId, Long playerId) {
        return this.teamKatalog.deletePlayer(teamId, playerId);
    }
    
}