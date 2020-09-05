package be.vdab.retrovideo.domain;

import javax.validation.constraints.NotBlank;

public class Klant {

    private final long id;
    @NotBlank
    private final String familienaam;
    @NotBlank
    private final String voornaam;
    @NotBlank
    private final String straatEnNummer;
    @NotBlank
    private final String postcode;
    @NotBlank
    private final String gemeente;

    public Klant(long id, @NotBlank String familienaam, @NotBlank String voornaam, @NotBlank String straatEnNummer, @NotBlank String postcode, @NotBlank String gemeente) {
        this.id = id;
        this.familienaam = familienaam;
        this.voornaam = voornaam;
        this.straatEnNummer = straatEnNummer;
        this.postcode = postcode;
        this.gemeente = gemeente;
    }


    public long getId() {
        return id;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getStraatEnNummer() {
        return straatEnNummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getGemeente() {
        return gemeente;
    }
}
