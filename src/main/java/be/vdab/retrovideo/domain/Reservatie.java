package be.vdab.retrovideo.domain;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservatie {

    @NotNull
    private final long klantId;
    @NotNull
    private final long filmId;
    @NotNull
    private final LocalDate reservatieDatum;

    public Reservatie(long klantId, long filmId, LocalDate reservatieDatum) {
        this.klantId = klantId;
        this.filmId = filmId;
        this.reservatieDatum = reservatieDatum;
    }

    public long getKlantId() {
        return klantId;
    }

    public long getFilmId() {
        return filmId;
    }

    public LocalDate getReservatieDatum() {
        return reservatieDatum;
    }

    @Override
    public String toString() {
        return klantId + " ; " + filmId + " ; " + reservatieDatum.toString();
    }
}
