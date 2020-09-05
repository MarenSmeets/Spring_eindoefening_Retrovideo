package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Genre;
import be.vdab.retrovideo.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
class DefaultGenreService implements GenreService {

    private final GenreRepository genreRepository;

    DefaultGenreService(GenreRepository repository) {
        this.genreRepository = repository;
    }

    @Override
    public List<Genre> findAllFromAtoZ() {
        return genreRepository.findAllFromAtoZ();
    }

    @Override
    public Optional<Genre> findById(long id){
        return genreRepository.findById(id);
    }
}
