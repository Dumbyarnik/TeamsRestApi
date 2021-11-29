package de.hsos.sportteam_api.models.team;

public class TeamDTO {

    public Long id;
    public String type;
    public TeamAttributesDTO attributes;

    public TeamDTO() {
        this.type = "teams";
    }

    public TeamDTO(Long id, TeamAttributesDTO attributes) {
        this.id = id;
        this.type = "teams";
        this.attributes = attributes;
    }

}
