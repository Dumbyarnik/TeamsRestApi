package de.hsos.sportteam_api.models.team;

import java.util.List;

import de.hsos.sportteam_api.entities.TeamKategorie;
import de.hsos.sportteam_api.models.person.PersonPostDTO;

public class TeamPostDTO{
    public Long id;
    public String name;
    public TeamKategorie category;
    public PersonPostDTO manager;
    public List<PersonPostDTO> players;

    public TeamPostDTO(){
        
    }

    public TeamPostDTO(Long id, String name, TeamKategorie category, 
        PersonPostDTO manager, List<PersonPostDTO> players){
        this.id = id;
        this.name = name;
        this.category = category;
        this.manager = manager;
        this.players = players;
    }
}