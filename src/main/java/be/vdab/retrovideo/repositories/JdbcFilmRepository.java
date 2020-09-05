package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcFilmRepository implements FilmRepository{

    private final JdbcTemplate template;
    private final RowMapper<Film> filmRowMapper =
            (result, rowNum) -> new Film(
                    result.getLong("id"),
                    result.getLong("genreid"),
                    result.getString("titel"),
                    result.getInt("voorraad"),
                    result.getInt("gereserveerd"),
                    result.getBigDecimal("prijs")
            );

    public JdbcFilmRepository(JdbcTemplate template) {
        this.template = template;
    }

    // geeft alle films van genre met gegeven genreID terug
    @Override
    public List<Film> findFilmByGenre(long genreId) {
        var sql="select id, genreid, titel, voorraad, gereserveerd, prijs from retrovideo.films where genreid=? order by titel";
        return template.query(sql, filmRowMapper, genreId);
    }

    // geeft de film met gegeven filmID terug als Optional
    @Override
    public Optional<Film> findFilmById(long id) {
        try {
            var sql = "select id, genreid, titel, voorraad, gereserveerd, prijs from retrovideo.films where id=? order by id";
            return Optional.of(template.queryForObject(sql, filmRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    // geeft alle films terug
    @Override
    public List<Film> findAll() {
        var sql="select id, genreid, titel, voorraad, gereserveerd, prijs from retrovideo.films order by id";
        return template.query(sql, filmRowMapper);
    }

    // verhoogt voor de gegeven film de kolom "gereserveerd" met 1 in de tabel films
    @Override
    public void reserveer(long id) {
        var sql = "update retrovideo.films set gereserveerd = (gereserveerd+1) where id=?";
        template.update(sql, id);
    }
}
