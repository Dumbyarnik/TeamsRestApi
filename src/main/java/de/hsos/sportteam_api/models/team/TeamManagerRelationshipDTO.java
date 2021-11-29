package de.hsos.sportteam_api.models.team;

import de.hsos.sportteam_api.models.person.ManagerRelationshipDTO;

public class TeamManagerRelationshipDTO extends TeamDTO {
    public ManagerRelationshipDTO relationships;

    public TeamManagerRelationshipDTO(){
        this.type = "teams";
    }

    public TeamManagerRelationshipDTO(Long id, TeamAttributesDTO attributes, ManagerRelationshipDTO managerRel) {
        this.id = id;
        this.type = "teams";
        this.attributes = attributes;
        this.relationships = managerRel;
    }
}
