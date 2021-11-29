package de.hsos.sportteam_api.entities;

import java.util.Objects;

import de.hsos.sportteam_api.models.person.PersonPostDTO;

public class Person{
    private Long id;
    private String name;

    public Person(String name){
        this.id = null;
        this.name = name;
    }

    public Person(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Person(PersonPostDTO person){
        this.id = person.id;
        this.name = person.name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person p = (Person) obj;
        return Objects.equals(this.id, p.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.id);
    }
}