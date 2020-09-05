package be.vdab.retrovideo.domain;

import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class Film {

    private final long id;
    @NotNull
    private final long genreId;
    @NotBlank
    private final String titel;
    @NotNull
    private final int voorraad;
    @NotNull
    private final int gereserveerd;
    @NotNull @PositiveOrZero @NumberFormat(pattern = "0.00")
    private final BigDecimal prijs;

    public Film(long id, long genreId, String titel, int voorraad, int gereserveerd, BigDecimal prijs) {
        this.id = id;
        this.genreId = genreId;
        this.titel = titel;
        this.voorraad = voorraad;
        this.gereserveerd = gereserveerd;
        this.prijs = prijs;
    }

    public long getId() {
        return id;
    }

    public long getGenreId() {
        return genreId;
    }

    public String getTitel() {
        return titel;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public int getGereserveerd() {
        return gereserveerd;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public int aantalBeschikbaar(){
        return voorraad - gereserveerd;
    }

}
