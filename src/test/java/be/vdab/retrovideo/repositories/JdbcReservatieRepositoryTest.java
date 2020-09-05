package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Reservatie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcReservatieRepository.class)
@Sql("/insertReservaties.sql")
class JdbcReservatieRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JdbcReservatieRepository reservatieRepository;
    private final static String RESERVATIES = "retrovideo.reservaties";

    JdbcReservatieRepositoryTest(JdbcReservatieRepository reservatieRepository) {
        this.reservatieRepository = reservatieRepository;
    }

    @Test
    void insertNieuweReservatie() {
        var reservatie = new Reservatie(3, 3, LocalDate.now());
        reservatieRepository.insertNieuweReservatie(reservatie);
        assertThat(super.countRowsInTableWhere(RESERVATIES, "klantid=" + 3 + " and filmid=" + 3)).isOne();
    }
}