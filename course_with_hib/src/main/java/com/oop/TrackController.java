package com.oop;

import com.oop.repos.GroupRepo;
import com.oop.repos.TrackRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class TrackController {
    @Autowired
    private TrackRepo trackRepo;

    @Autowired
    private GroupRepo groupRepo;

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping("/tracks")
    public String main(Model model){
        logger.trace("Main method accessed");
        Iterable<Group> groups = groupRepo.findAll();
        Iterable<Track> tracks = trackRepo.findAll();
        model.addAttribute("tracks", tracks);
        model.addAttribute("groups", groups);
        return "tracks";
    }
    @GetMapping("/tracks/new")
    public String addNewTrack(Model model){
        logger.trace("addNewTrack method accessed");
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("track", new Track());
        model.addAttribute("groups", groups);

        return "track-new";
    }

    @PostMapping("/tracks/save")
    public String savePerson(@Valid Track track, BindingResult bindingResult, Model model){
        logger.trace("savePerson method accessed");
        if( bindingResult.hasErrors()) {
            logger.error("Errors found");
            Iterable<Group> groups = groupRepo.findAll();
            model.addAttribute("track", track);
            model.addAttribute("groups", groups);
            return "track-new";
        }
        trackRepo.save(track);

        return "redirect:/tracks";
    }

    @PostMapping("filterTrack")
    public String filterTrack(@RequestParam String filter, Model model){
        logger.trace("filterTrack method accessed");
        Iterable<Track> tracks;
        if (filter != null && !filter.isEmpty()){
            logger.trace("Found");
            tracks = trackRepo.findByName(filter);
        }
        else{
            logger.trace("Not found");
            tracks = trackRepo.findAll();
        }
        model.addAttribute("tracks", tracks);
        return "tracks";
    }

    @GetMapping("/tracks/{id}edit")
    public String trackEdit(@PathVariable(value = "id") int id, Model model){
        logger.trace("trackEdit method accessed");
        if(!trackRepo.existsById(id)) {
            logger.trace("This concert not found");
            return "redirect:/tracks";
        }
        Track track = trackRepo.findById(id).get();
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("track", track);
        model.addAttribute("groups", groups);
        return "track-new";
    }

    @GetMapping("/tracks/{id}delete")
    public String trackDelete(@PathVariable(value = "id") int id, Model model){
        logger.trace("trackDelete method accessed");

        trackRepo.deleteById(id);

        return "redirect:/tracks";
    }

}
