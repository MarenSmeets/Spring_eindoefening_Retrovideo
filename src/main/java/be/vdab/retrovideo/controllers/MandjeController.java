package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.domain.Reservatie;
import be.vdab.retrovideo.services.FilmService;
import be.vdab.retrovideo.services.KlantService;
import be.vdab.retrovideo.services.ReservatieService;
import be.vdab.retrovideo.sessions.Mandje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
@RequestMapping("mandje")
class MandjeController {

    private final Mandje mandje;
    private final FilmService filmService;
    private final KlantService klantService;
    private final ReservatieService reservatieService;
    private long klantId;

     MandjeController(Mandje mandje, FilmService filmService, KlantService klantService, ReservatieService reservatieService) {
        this.mandje = mandje;
        this.filmService = filmService;
         this.klantService = klantService;
         this.reservatieService = reservatieService;
     }

     // geeft de inhoud van het mandje mee
    @GetMapping
    public ModelAndView toonMandje() {
        var alleFilms = filmService.findAll();
        var modelAndView = new ModelAndView("mandje");
        if (mandje.isGevuld()) {
            modelAndView.addObject("filmsInMandje",
                    alleFilms.stream()
                            .filter(film -> mandje.bevat(film.getId()))
                            .collect(Collectors.toList()));
        }
        return modelAndView;
    }

    // voeg een film toe aan het mandje
    @PostMapping("toevoegen")
    public String voegToe(long id){
        if(!mandje.bevat(id)){
            mandje.voegToe(id);
        }
        return "redirect:/mandje";
    }

    // verwijder een film uit het mandje
    @PostMapping("verwijderen")
    public String verwijder(long[] id) {
        if(id!=null){
            mandje.verwijder(id);
        }
        return "redirect:/mandje";
    }

    // bevestigen
    // geeft aantal films in het mandje en de naam van de klant mee
    @GetMapping("bevestigen/{id}")
    public ModelAndView toonAantalFilmsVoorDezeKlant(@PathVariable long id){
         klantId = id;
         var modelAndView = new ModelAndView("bevestigen");
         modelAndView.addObject("aantalFilms", mandje.bevatAantal());
         var klant = klantService.findById(id).get().getVoornaam()
                 + " "
                 + klantService.findById(id).get().getFamilienaam();
         modelAndView.addObject("klant", klant);
         return modelAndView;
    }

    // voert de reservatie van de films voor de gekozen klant uit
    // indien voorraad > gereserveerd
    // als de reservatie niet lukt voor bepaalde films , worden deze doorgegeven aan rapport
    // na afloop wordt het mandje leeggemaakt
    @PostMapping("reserveer")
    public ModelAndView reserveren(){
         var idsInMandje = mandje.getIds();
         var mislukt = new ArrayList<String>();
         for(var filmId:idsInMandje){
             if(filmService.findFilmById(filmId).get().aantalBeschikbaar() <= 0) {
                 mislukt.add(filmService.findFilmById(filmId).get().getTitel());
             } else {
                 var reservatie = new Reservatie(klantId, filmId, LocalDate.now());
                 reservatieService.insertNieuweReservatie(reservatie);
                 filmService.reserveer(filmId);
             }
         }
         mandje.leegmaken();
         var modelAndView = new ModelAndView("rapport");
         modelAndView.addObject("mislukteReservaties", mislukt);
         return modelAndView;
    }
}

