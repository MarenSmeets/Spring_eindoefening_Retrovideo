package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.forms.KlantForm;
import be.vdab.retrovideo.services.KlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("klanten")
public class KlantController {

    private final KlantService klantService;

    public KlantController(KlantService klantService) {
        this.klantService = klantService;
    }

    // maakt gebruik van het custom formulier "KlantForm.java"
    @GetMapping
    public ModelAndView klantForm() {
        return new ModelAndView("klanten")
                .addObject(new KlantForm(null));
    }

    // zoekt in de databank naar klanten waarvan de familienaam de gegeven string bevat en
    // geeft de resultaten van de zoekopdracht mee
    @GetMapping("findByNaamBevat")
    public ModelAndView findByNaamBevat(@Valid KlantForm klantForm, Errors errors) {
        var modelAndView = new ModelAndView("klanten");
        if (errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject("klanten",
                klantService.findByNaamBevat(klantForm.getDeelVanNaam()));
    }

}
