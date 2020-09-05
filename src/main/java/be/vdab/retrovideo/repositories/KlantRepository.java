package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;

import java.util.List;
import java.util.Optional;

public interface KlantRepository {

    List<Klant> findByNaamBevat(String deelVanNaam);
    Optional<Klant> findById(long id);
}
