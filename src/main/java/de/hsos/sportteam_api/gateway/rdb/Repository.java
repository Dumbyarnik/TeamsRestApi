package de.hsos.sportteam_api.gateway.rdb;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import de.hsos.sportteam_api.entities.Person;
import de.hsos.sportteam_api.entities.Team;
import de.hsos.sportteam_api.entities.TeamKategorie;
import de.hsos.sportteam_api.entities.TeamsKatalog;
import de.hsos.sportteam_api.models.person.PersonPostDTO;
import de.hsos.sportteam_api.models.team.TeamPostDTO;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Repository implements TeamsKatalog {

    private List<Team> teams;

    public Repository() {
        this.teams = new ArrayList<>();
        List<Person> players = new ArrayList<>(Arrays.asList(new Person(Long.valueOf(1), "David Schmidt"),
                new Person(Long.valueOf(2), "Dennis Müller")));
        this.teams.add(new Team(Long.valueOf(0), "Kiddie Kicker", TeamKategorie.JUNIORS,
                new Person(Long.valueOf(0), "Trainer Boris"), players));

        players = new ArrayList<>(Arrays.asList(new Person(Long.valueOf(4), "Peter Mustermann")));
        this.teams.add(new Team(Long.valueOf(1), "FC HS Osnabrück", TeamKategorie.SENIORS,
                new Person(Long.valueOf(3), "Trainer Hans"), players));

        players = new ArrayList<>(Arrays.asList(new Person(Long.valueOf(6), "Kevin Möhwald"),
                new Person(Long.valueOf(7), "Ludwig Augustinsson")));
        this.teams.add(new Team(Long.valueOf(2), "Werder Bremen", TeamKategorie.MASTERS,
                new Person(Long.valueOf(5), "Trainer Jens"), players));

        players = new ArrayList<>(Arrays.asList(new Person(Long.valueOf(6), "Julia Schuster"),
                new Person(Long.valueOf(7), "Greta Becker")));
        this.teams.add(new Team(Long.valueOf(3), "Handball Frauenliga Osnabrück", TeamKategorie.MASTERS,
                new Person(Long.valueOf(5), "Trainerin Jennifer"), players));
    }

    @Override
    public List<Team> getTeams() {
        return this.teams;
    }

    @Override
    public Team getTeam(Long id) {
        for (Team tmp : teams) {
            if (tmp.getId().equals(id))
                return tmp;
        }
        return null;
    }

    @Override
    public Team addTeam(TeamPostDTO team) {
        Team newTeam = new Team(team);
        System.out.println(team.id);

        if (this.teams.contains(newTeam)) {
            return null;
        }
        this.teams.add(newTeam);
        
        return newTeam;
    }

    @Override
    public Team updateTeam(TeamPostDTO team) {
        Team newTeam = new Team(team);
        int index = this.teams.indexOf(newTeam);
        
        if (index == -1) {
            return null;
        }

        Team tmp = this.teams.get(index);
        tmp.setName(newTeam.getName());
        tmp.setCategory(newTeam.getCategory());
        tmp.setManager(newTeam.getManager());
        tmp.setPlayers(newTeam.getPlayers());
        return tmp;
    }

    @Override
    public boolean deleteTeam(Long id) {

        for (Team tmp : teams) {
            if(tmp.getId().equals(id))
                return true;
        }

        return false;
    }

    @Override
    public Person addPlayer(Long teamId, PersonPostDTO player) {
        Person newPlayer = new Person(player);
        Team team = this.getTeam(teamId);

        if (team == null || team.getPlayers().contains(newPlayer)) {
            return null;
        }
        
        team.getPlayers().add(newPlayer);
        return newPlayer;
    }

    @Override
    public boolean deletePlayer(Long teamId, Long playerId) {
        Team team = this.getTeam(teamId);
        
        if (team != null)
            for (Person tmp : team.getPlayers()) {
                if(tmp.getId().equals(playerId)){
                    team.getPlayers().remove(tmp);
                    return true;
                }
                
            }
        return false;
    }

}