package de.hsos.sportteam_api.models.team;

import de.hsos.sportteam_api.models.person.ManagerUndPlayerRelationshipDTO;

public class TeamAllRelationshipsDTO extends TeamDTO {
    
    public ManagerUndPlayerRelationshipDTO relationships;

    public TeamAllRelationshipsDTO(){
        this.type = "teams";
    }

    public TeamAllRelationshipsDTO(Long id, TeamAttributesDTO attributes, ManagerUndPlayerRelationshipDTO rel) {
        this.id = id;
        this.type = "teams";
        this.attributes = attributes;
        this.relationships = rel;
    }

}
