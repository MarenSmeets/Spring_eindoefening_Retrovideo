package be.vdab.retrovideo.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@SessionScope
public class Mandje implements Serializable {

    private final static long serialVersionUID = 1L;

    // bewaart enkel de IDs van de films die in het mandje zitten
    private final Set<Long> ids = new LinkedHashSet<>();

    public Set<Long> getIds(){
        return ids;
    }

    public void verwijder(long[] idsToDelete){
        Arrays.stream(idsToDelete).forEach(id -> ids.remove(id));
    }

    public void voegToe(long id){
        ids.add(id);
    }

    public boolean bevat(long id){
        return ids.contains(id);
    }

    public int bevatAantal(){
        return ids.size();
    }

    public boolean isGevuld(){
        return !ids.isEmpty();
    }

    public void leegmaken() {
        ids.clear();
    }

}
