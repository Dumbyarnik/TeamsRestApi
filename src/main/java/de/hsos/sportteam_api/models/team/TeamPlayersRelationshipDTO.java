package de.hsos.sportteam_api.models.team;

import de.hsos.sportteam_api.models.person.PlayersRelationshipDTO;

public class TeamPlayersRelationshipDTO extends TeamDTO{
    
    public PlayersRelationshipDTO relationships;

    public TeamPlayersRelationshipDTO(){
        this.type = "teams";
    }

    public TeamPlayersRelationshipDTO(Long id, TeamAttributesDTO attributes, PlayersRelationshipDTO playersRel) {
        this.id = id;
        this.type = "teams";
        this.attributes = attributes;
        this.relationships = playersRel;
    }
}
