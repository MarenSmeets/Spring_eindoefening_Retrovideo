package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Klant;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcKlantRepository implements KlantRepository {

    private final JdbcTemplate template;
    private final RowMapper<Klant> klantRowMapper = (result, rowNum) ->
            new Klant(
                    result.getLong("id"),
                    result.getString("familienaam"),
                    result.getString("voornaam"),
                    result.getString("straatNummer"),
                    result.getString("postcode"),
                    result.getString("gemeente")
            );

    public JdbcKlantRepository(JdbcTemplate template) {
        this.template = template;
    }

    // geeft een lijst terug van klanten waarvan de familienaam de gegeven string bevat
    @Override
    public List<Klant> findByNaamBevat(String deelVanNaam) {
        var sql = "select id, familienaam, voornaam, straatNummer, postcode, gemeente" +
                " from retrovideo.klanten where familienaam like ? order by familienaam";
        return template.query(sql, klantRowMapper, "%" + deelVanNaam + "%");
    }

    // geeft de klant met gegeven ID terug als Optional
    @Override
    public Optional<Klant> findById(long id) {
        try {
            var sql="select id, familienaam, voornaam, straatNummer, postcode, gemeente from retrovideo.klanten where id=?";
            return Optional.of(template.queryForObject(sql, klantRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }
}
