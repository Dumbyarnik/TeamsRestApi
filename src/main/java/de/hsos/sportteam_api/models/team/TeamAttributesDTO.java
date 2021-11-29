package de.hsos.sportteam_api.models.team;

import de.hsos.sportteam_api.entities.TeamKategorie;

public class TeamAttributesDTO {

    public String name;
    public TeamKategorie category;

    public TeamAttributesDTO() {}

    public TeamAttributesDTO(String name, TeamKategorie category) {
        this.name = name;
        this.category = category;
    }

}
