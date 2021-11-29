package de.hsos.sportteam_api.models.person;

public class PersonPostDTO {
    
    public Long id;
    public String name;

    public PersonPostDTO(){}

    //Für neuen Spieler hinzufuegen
    public PersonPostDTO(String name){
        this.name = name;
    }

    public PersonPostDTO(Long id, String name){
        this.id = id;
        this.name = name;
    }

}
