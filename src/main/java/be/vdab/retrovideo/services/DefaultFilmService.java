package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Film;
import be.vdab.retrovideo.repositories.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
class DefaultFilmService implements FilmService {

    private final FilmRepository filmRepository;

    DefaultFilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> findFilmByGenre(long genreId) {
        return filmRepository.findFilmByGenre(genreId);
    }

    @Override
    public Optional<Film> findFilmById(long id) {
        return filmRepository.findFilmById(id);
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }


    @Override
    @Transactional(readOnly = false) // dit is de enige method die aanpassingen kan doen in de databank
    public void reserveer(long id) {
        filmRepository.reserveer(id);
    }
}
