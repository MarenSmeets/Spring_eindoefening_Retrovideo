package be.vdab.retrovideo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcGenreRepository.class)
@Sql("/insertGenres.sql")
class JdbcGenreRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String GENRES = "retrovideo.genres";
    private final JdbcGenreRepository repository;

    JdbcGenreRepositoryTest(JdbcGenreRepository repository) {
        this.repository = repository;
    }


    @Test
    void findAllFromAtoZ() {
        assertThat(repository.findAllFromAtoZ())
                .hasSize(super.countRowsInTable(GENRES))
                .extracting(genre -> genre.getNaam().toLowerCase())
                .isSorted();
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestGenre()).get().getNaam()).isEqualTo("test1");
    }

    private long idVanTestGenre() {
        return super.jdbcTemplate.queryForObject(
                "select id from retrovideo.genres where naam='test1' ", Long.class
        );
    }
}