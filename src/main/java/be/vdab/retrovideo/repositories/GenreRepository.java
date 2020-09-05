package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAllFromAtoZ();
    Optional<Genre> findById(long id);
}
