package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Genre;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    private final JdbcTemplate template;
    private final RowMapper<Genre> genreMapper =
            (result, rowNum) -> new Genre(
                    result.getLong("id"),
                    result.getString("naam")
                    );

    public JdbcGenreRepository(JdbcTemplate template) {
        this.template = template;
    }


    // geeft alle genres terug, alfabetisch gesorteerd
    @Override
    public List<Genre> findAllFromAtoZ() {
        var sql="select id, naam from retrovideo.genres order by naam";
        return template.query(sql, genreMapper);
    }

    // geeft het genre met gegeven id terug als Optional
    @Override
    public Optional<Genre> findById(long id) {
        try {
            var sql ="select id, naam from retrovideo.genres where id=?";
            return Optional.of(template.queryForObject(sql, genreMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }
}
