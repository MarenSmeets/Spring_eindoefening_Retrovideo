package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {

    List<Film> findFilmByGenre(long genreId);
    Optional<Film> findFilmById(long id);
    List<Film> findAll();
    void reserveer(long id);
}
