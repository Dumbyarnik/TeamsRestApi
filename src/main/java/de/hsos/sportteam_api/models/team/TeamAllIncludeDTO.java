package de.hsos.sportteam_api.models.team;

import java.util.List;

import de.hsos.sportteam_api.models.person.ManagerUndPlayerRelationshipDTO;
import de.hsos.sportteam_api.models.person.PersonWithAttributesDTO;

public class TeamAllIncludeDTO extends TeamAllRelationshipsDTO {

    public List<PersonWithAttributesDTO> included;

    public TeamAllIncludeDTO(){
        this.type = "teams";
    }

    public TeamAllIncludeDTO(Long id, TeamAttributesDTO attributes, ManagerUndPlayerRelationshipDTO rel, List<PersonWithAttributesDTO> persons) {
        this.id = id;
        this.type = "teams";
        this.attributes = attributes;
        this.relationships = rel;
        this.included = persons;
    }

}
