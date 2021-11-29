package de.hsos.sportteam_api.acl;

import de.hsos.sportteam_api.control.SpielerpassQualifier;
import de.hsos.sportteam_api.control.SpielerpassType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@SpielerpassQualifier(type = SpielerpassType.ATOMIC)
public class Spielerpass implements Serializable, Passwesen {
    private static final long serialVersionUID= 1L; // Needed, because we want our playerID unique. INFO: https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
    AtomicLong playerID;

    /**
     * @param counter Used @ConfigProperty to import min value from application.properties
     */
    public Spielerpass(@ConfigProperty(name="min_player_id", defaultValue= "10") Long counter) {
        this.playerID = new AtomicLong(counter);
    }

    @Override
    public Long getPlayerId() {
        return this.playerID.getAndIncrement();
    }
    
}