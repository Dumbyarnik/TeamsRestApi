package de.hsos.sportteam_api.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hsos.sportteam_api.models.team.TeamPostDTO;

public class Team {
    private Long id;
    private String name;
    private TeamKategorie category;
    private Person manager;
    private List<Person> players = new ArrayList<>();

    public Team(TeamPostDTO team){
        this.id = team.id;
        this.name = team.name;
        this.category = team.category;
        this.manager = new Person(team.manager);
        team.players.forEach(player -> this.players.add(new Person(player)));
    }

    public Team(Long id, String name, TeamKategorie category, Person manager, List<Person> players){
        this.id = id;
        this.name = name;
        this.category = category;
        this.manager = manager;
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamKategorie getCategory() {
        return category;
    }

    public void setCategory(TeamKategorie category) {
        this.category = category;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }

    public List<Person> getPlayers() {
        return players;
    }

    public void setPlayers(List<Person> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Team t = (Team) obj;
        return Objects.equals(this.id, t.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.id, this.category, this.manager, this.players);
    }
}