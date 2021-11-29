package de.hsos.sportteam_api.control;

import java.util.List;

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

public interface Verwaltung {
    public List<TeamDTO> getTeams();
    public TeamDTO getTeam(Long id);
    public TeamManagerRelationshipDTO getTeamManagerRelationshipDTO(Long id);
    public TeamPlayersRelationshipDTO getTeamPlayersRelationshipDTO(Long id);
    public TeamManagerIncludeDTO getTeamManagerInclude(Long id);
    public TeamPlayersIncludeDTO getTeamPlayersInclude(Long id);
    public TeamAllIncludeDTO getTeamAllInclude(Long id);
    public List<PersonWithAttributesDTO> getPlayers(Long teamId);
    public PersonWithAttributesDTO getManager(Long teamId);

    public TeamDTO addTeam(TeamPostDTO team);
    public TeamDTO updateTeam(TeamPostDTO team);
    public boolean deleteTeam(Long id);
    public PersonWithAttributesDTO addPlayer(Long teamId, PersonPostDTO player);
    public boolean deletePlayer(Long teamId, Long playerId);
    

}