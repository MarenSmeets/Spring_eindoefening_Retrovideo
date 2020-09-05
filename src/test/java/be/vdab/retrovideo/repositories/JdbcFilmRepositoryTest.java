package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Film;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcFilmRepository.class)
@Sql("/insertFilms.sql")
class JdbcFilmRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final static String FILMS = "retrovideo.films";
    private final JdbcFilmRepository filmRepository;

    JdbcFilmRepositoryTest(JdbcFilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Test
    void findFilmByGenre() {
        assertThat(filmRepository.findFilmByGenre(2))
                .hasSize(super.countRowsInTableWhere(FILMS, "genreid=2"));
    }

    @Test
    void findByOnbestaandGenreVindtGeenFilm() {
        assertThat(filmRepository.findFilmByGenre(-1)).hasSize(0);
    }

    @Test
    void findFilmById(){
        var id= idVanTestFilm();
        assertThat(filmRepository.findFilmById(id).get().getTitel()).isEqualTo("test1");
    }

    @Test
    void findByOnbestaandeIdVindtGeenFilm() {
        assertThat(filmRepository.findFilmById(-1)).isEmpty();
    }

    private long idVanTestFilm(){
        return super.jdbcTemplate.queryForObject(
                "select id from retrovideo.films where titel ='test1'", Long.class);
    }

    @Test
    void findAll(){
        assertThat(filmRepository.findAll())
                .hasSize(super.countRowsInTable(FILMS))
                .extracting(Film::getId)
                .isSorted();
    }
}