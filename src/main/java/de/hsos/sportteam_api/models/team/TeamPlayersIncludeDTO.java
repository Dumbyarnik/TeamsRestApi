package de.hsos.sportteam_api.models.team;

import java.util.List;

import de.hsos.sportteam_api.models.person.PersonWithAttributesDTO;
import de.hsos.sportteam_api.models.person.PlayersRelationshipDTO;

public class TeamPlayersIncludeDTO extends TeamPlayersRelationshipDTO {
    
    public List<PersonWithAttributesDTO> included;

    public TeamPlayersIncludeDTO(){
        this.type = "teams";
    }

    public TeamPlayersIncludeDTO(Long id, TeamAttributesDTO attributes, PlayersRelationshipDTO playerRel, List<PersonWithAttributesDTO> persons) {
        this.id = id;
        this.type = "teams";
        this.attributes = attributes;
        this.relationships = playerRel;
        this.included = persons;
    }
}
