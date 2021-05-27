package com.oop;

import com.oop.repos.ConcertRepo;
import com.oop.repos.GroupRepo;
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
public class ConcertController {
    @Autowired
    private ConcertRepo concertRepo;

    @Autowired
    private GroupRepo groupRepo;

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping("/concerts")
    public String main(Model model){
        logger.trace("Main method accessed");
        Iterable<Group> groups = groupRepo.findAll();
        Iterable<Concert> concerts = concertRepo.findAll();
        model.addAttribute("concerts", concerts);
        model.addAttribute("groups", groups);
        return "concerts";
    }
    @GetMapping("/concerts/new")
    public String addNewConcert(Model model){
        logger.trace("addNewConcert method accessed");

        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("concert", new Concert());
        model.addAttribute("groups", groups);

        return "concert-new";
    }

    @PostMapping("/concerts/save")
    public String saveConcert(@Valid Concert concert, BindingResult bindingResult, Model model){
        logger.trace("saveConcert method accessed");
        if( bindingResult.hasErrors()) {
            logger.error("Errors found");

            Iterable<Group> groups = groupRepo.findAll();
            model.addAttribute("concert", concert);
            model.addAttribute("groups", groups);
            return "concert-new";
        }
        concertRepo.save(concert);

        return "redirect:/concerts";
    }

    @PostMapping("filterConcerts")
    public String filterConcert(@RequestParam String filter, Model model){
        logger.trace("filterConcert method accessed");
        Iterable<Concert> concerts;
        if (filter != null && !filter.isEmpty() && groupRepo.findByName(filter) != null){
            logger.trace("Found");
            Group groups = groupRepo.findByName(filter).get(0);
            concerts = concertRepo.findByGroup(groups);
            model.addAttribute("concerts", concerts);
            return "concerts";
        }
        else{
            logger.trace("Not found");
            concerts = concertRepo.findAll();
            return "redirect:/concerts";
        }
    }

    @GetMapping("/concerts/{id}edit")
    public String concertEdit(@PathVariable(value = "id") int id, Model model){
        logger.trace("concertEdit method accessed");

        if(!concertRepo.existsById(id)) {
            logger.trace("This concert not found");
            return "redirect:/concerts";
        }
        Concert concert = concertRepo.findById(id).get();
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("concert", concert);
        model.addAttribute("groups", groups);
        return "concert-new";
    }

    @GetMapping("/concerts/{id}delete")
    public String concertDelete(@PathVariable(value = "id") int id, Model model){
        logger.trace("concertDelete method accessed");

        concertRepo.deleteById(id);

        return "redirect:/concerts";
    }

}
