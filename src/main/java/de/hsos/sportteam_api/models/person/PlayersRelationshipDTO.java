package de.hsos.sportteam_api.models.person;

import java.util.List;

public class PlayersRelationshipDTO {

    public List<PersonDTO> players;

    public PlayersRelationshipDTO() {}

    public PlayersRelationshipDTO(List<PersonDTO> players) {
        this.players = players;
    }

}
