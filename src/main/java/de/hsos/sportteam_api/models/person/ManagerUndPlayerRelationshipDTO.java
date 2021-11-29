package de.hsos.sportteam_api.models.person;

import java.util.List;

public class ManagerUndPlayerRelationshipDTO extends ManagerRelationshipDTO {

    public List<PersonDTO> players;

    public ManagerUndPlayerRelationshipDTO(){}

    public ManagerUndPlayerRelationshipDTO(List<PersonDTO> players, PersonDTO manager) {
        this.players = players;
        this.manager = manager;
    }

}
