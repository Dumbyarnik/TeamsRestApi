package de.hsos.sportteam_api.models.team;

import java.util.List;

import de.hsos.sportteam_api.models.person.ManagerRelationshipDTO;
import de.hsos.sportteam_api.models.person.PersonWithAttributesDTO;

public class TeamManagerIncludeDTO extends TeamManagerRelationshipDTO {
    
    public List<PersonWithAttributesDTO> included;

    public TeamManagerIncludeDTO(){
        this.type = "teams";
    }

    public TeamManagerIncludeDTO(Long id, TeamAttributesDTO attributes, 
            ManagerRelationshipDTO managerRelationship, 
            List<PersonWithAttributesDTO> persons) {

        this.id = id;
        this.type = "teams";
        this.attributes = attributes;
        this.relationships = managerRelationship;
        this.included = persons;
    }
}
