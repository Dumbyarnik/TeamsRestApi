package de.hsos.sportteam_api.models.person;

public class PersonWithAttributesDTO extends PersonDTO {
    
    public PersonAttributesDTO attributes;

    public PersonWithAttributesDTO(){
        this.type = "persons";
    }

    public PersonWithAttributesDTO(Long id, PersonAttributesDTO attributes){
        this.id = id;
        this.type = "persons";
        this.attributes = attributes;
    }
}