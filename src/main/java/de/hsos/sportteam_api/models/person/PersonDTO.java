package de.hsos.sportteam_api.models.person;

public class PersonDTO{
    
    public Long id;
    public String type;

    public PersonDTO(){
        this.type = "persons";
    }

    public PersonDTO(Long id){
        this.id = id;
        this.type = "persons";
    }
}