package de.hsos.sportteam_api.models.person;

public class ManagerRelationshipDTO {

    public PersonDTO manager;

    public ManagerRelationshipDTO() {}

    public ManagerRelationshipDTO(PersonDTO manager) {
        this.manager = manager;
    }

}
