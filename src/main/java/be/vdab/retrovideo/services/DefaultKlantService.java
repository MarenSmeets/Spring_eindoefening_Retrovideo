package be.vdab.retrovideo.services;

import be.vdab.retrovideo.domain.Klant;
import be.vdab.retrovideo.repositories.KlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DefaultKlantService implements KlantService {

    private final KlantRepository klantRepository;

    public DefaultKlantService(KlantRepository klantRepository) {
        this.klantRepository = klantRepository;
    }

    @Override
    public List<Klant> findByNaamBevat(String deelVanNaam) {
        return klantRepository.findByNaamBevat(deelVanNaam);
    }

    @Override
    public Optional<Klant> findById(long id) {
        return klantRepository.findById(id);
    }
}
