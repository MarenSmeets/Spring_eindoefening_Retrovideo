package be.vdab.retrovideo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import(JdbcKlantRepository.class)
@Sql("/insertKlanten.sql")
class JdbcKlantRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final static String KLANTEN = "retrovideo.klanten";
    private final JdbcKlantRepository klantRepository;

    JdbcKlantRepositoryTest(JdbcKlantRepository klantRepository) {
        this.klantRepository = klantRepository;
    }

    @Test
    void findByNaamBevat() {
        var deelVanNaam = "et";
        assertThat(klantRepository.findByNaamBevat(deelVanNaam))
                .hasSize(super.countRowsInTableWhere(KLANTEN, "familienaam like '%"+deelVanNaam+"%' "))
                .extracting(klant -> klant.getFamilienaam().toLowerCase() )
                .isSorted();
    }

    @Test
    void findById(){
        assertThat(klantRepository.findById(idVanTestKlant()).get().getFamilienaam()).isEqualTo("Smeets");
    }

    private long idVanTestKlant(){
        return super.jdbcTemplate.queryForObject(
                "select id from retrovideo.klanten where familienaam='Smeets' ", Long.class
        );
    }
}