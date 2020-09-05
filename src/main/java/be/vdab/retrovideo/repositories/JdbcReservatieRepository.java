package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Reservatie;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcReservatieRepository implements ReservatieRepository {

    private final static String RESERVATIES = "retrovideo.reservaties";
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<Reservatie> reservatieMapper =
            (result, rowNum) -> new Reservatie(
                    result.getLong("klantid"),
                    result.getLong("filmid"),
                    result.getDate("reservatie").toLocalDate()
            );

    public JdbcReservatieRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName(RESERVATIES);
    }

    // voegt nieuwe reservatie enkel toe als deze nog niet bestaat in de databank
    @Override
    public void insertNieuweReservatie(Reservatie reservatie) {
        if(bestaandeReservatie(reservatie).isEmpty()) {
            var values = Map.of(
                    "klantid", reservatie.getKlantId(),
                    "filmid", reservatie.getFilmId(),
                    "reservatie", LocalDate.now()
            );
             insert.execute(values);
        }
    }

    // controleert of de gegeven reservatie reeds bestaat in de databank en geeft een Optional terug
    private Optional<Reservatie> bestaandeReservatie(Reservatie reservatie){
        var klantId = reservatie.getKlantId();
        var filmId = reservatie.getFilmId();
        var datum = reservatie.getReservatieDatum();
        try {
            var sql = "select klantid, filmid, reservatie from retrovideo.reservaties where klantid = ? and filmid = ? and reservatie = ? order by klantid";
            return Optional.of(template.queryForObject(sql, reservatieMapper, klantId, filmId, datum));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

}
