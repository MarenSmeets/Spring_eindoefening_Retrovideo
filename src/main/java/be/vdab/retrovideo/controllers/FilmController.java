package be.vdab.retrovideo.controllers;

import be.vdab.retrovideo.services.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("film")
public class FilmController {

    private final FilmService filmService;
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    //geeft de film met gegeven ID mee als deze gevonden wordt in de databank
    @GetMapping("{id}")
    public ModelAndView filmById(@PathVariable long id) {
        var modelAndView = new ModelAndView("film");
        filmService.findFilmById(id).ifPresent(film -> modelAndView.addObject("film", film));
        return modelAndView;
    }

}
