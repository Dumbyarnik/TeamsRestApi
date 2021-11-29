package de.hsos.sportteam_api.entities;

import java.util.List;

import de.hsos.sportteam_api.models.person.PersonPostDTO;
import de.hsos.sportteam_api.models.team.TeamPostDTO;

public interface TeamsKatalog{
    public List<Team> getTeams();
    public Team getTeam(Long id);
    public Team addTeam(TeamPostDTO team);
    public Team updateTeam(TeamPostDTO team);
    public boolean deleteTeam(Long id);
    public Person addPlayer(Long teamId, PersonPostDTO player);
    public boolean deletePlayer(Long teamId, Long playerId);
}