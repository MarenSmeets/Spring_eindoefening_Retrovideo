package be.vdab.retrovideo.forms;

import javax.validation.constraints.NotBlank;

public class KlantForm {

    @NotBlank
    private final String deelVanNaam;

    public KlantForm(String deelVanNaam) {
        this.deelVanNaam = deelVanNaam;
    }

    public String getDeelVanNaam() {
        return deelVanNaam;
    }
}
