package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.services.FilmService;
import be.vdab.retrovideo.services.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class GenreController {

    private final GenreService genreService;
    private final FilmService filmService;

    public GenreController(GenreService genreService, FilmService filmService) {
        this.genreService = genreService;
        this.filmService = filmService;
    }

    // geef overzicht van alle genres op de homepagina
    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("index", "genres", genreService.findAllFromAtoZ());
    }

    // zoekt het genre met gegeven ID op in de databank en
    // geeft een lijst van alle films van dit genre mee
    @GetMapping("genre/{genreId}")
    public ModelAndView filmsPerGenre(@PathVariable long genreId) {
        var modelAndView = new ModelAndView("genre", "genres", genreService.findAllFromAtoZ());
        genreService.findById(genreId).ifPresent(genre -> modelAndView.addObject("genre", genre));
        modelAndView.addObject("filmsByGenre", filmService.findFilmByGenre(genreId));
        return modelAndView;
    }
}
