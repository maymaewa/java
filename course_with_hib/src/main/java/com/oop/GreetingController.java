package com.oop;

import com.oop.repos.ConcertRepo;
import com.oop.repos.GroupRepo;
import com.oop.repos.PersonRepo;
import com.oop.repos.TrackRepo;
import org.apache.catalina.filters.ExpiresFilter;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GreetingController {
    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private ConcertRepo concertRepo;

    @Autowired
    private TrackRepo trackRepo;

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping
    public String main(Model model){
        logger.trace("Main method accessed");
        Iterable<Group> groups = groupRepo.findByOrderByRatingAsc();
        model.addAttribute("groups", groups);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model){
        logger.trace("filter method accessed");
        Iterable<Group> groups;
        if (filter != null && !filter.isEmpty()){
            logger.trace("Found");
            groups = groupRepo.findByName(filter);
        }
        else{
            groups = groupRepo.findAll();
            logger.trace("Not found");
        }
        model.addAttribute("groups", groups);
        return "main";
    }


    @GetMapping("/{id}edit")
    public String groupEdit(@PathVariable(value = "id") int id, Model model){
        logger.trace("groupEdit method accessed");
        if(!groupRepo.existsById(id)) {
            logger.trace("This group not found");
            return "redirect:/main";
        }
        Group group = groupRepo.findById(id).get();
        model.addAttribute("group", group);
        return "group-new";
    }


    @GetMapping("/new")
    public String addNewGroup(Model model){
        logger.trace("addNewGroup method accessed");
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("group", new Group());

        return "group-new";
    }

    @PostMapping("/save")
    public String saveGroup(@Valid Group group, BindingResult bindingResult, Model model){
        logger.trace("saveGroup method accessed");
        if( bindingResult.hasErrors()) {
            logger.error("Errors found");
            model.addAttribute("group", group);
            return "group-new";
        }
        groupRepo.save(group);

        return "redirect:/";
    }


    @PostMapping("/{id}remove")
    public String groupDelete(@PathVariable(value = "id") Group group, Model model){
        logger.trace("groupDelete method accessed");

        Iterable<Person> persons = personRepo.findByGroup(group);
        for(Person p : persons){
            personRepo.delete(p);
        }
        logger.trace("groups composition was removed");

        Iterable<Track> tracks = trackRepo.findByGroup(group);
        for(Track p : tracks){
            trackRepo.delete(p);
        }
        logger.trace("groups repertoire removed");

        Iterable<Concert> concerts = concertRepo.findByGroup(group);
        for(Concert p : concerts){
            concertRepo.delete(p);
        }

        logger.trace("groups schedule was removed");
        groupRepo.delete(group);
        logger.trace("group was removed");
        return "redirect:";
    }


    @GetMapping("/{id}info")
    public String groupInfo(@PathVariable(value = "id") int id, Model model){
        logger.trace("groupInfo method accessed");
        if(!groupRepo.existsById(id)) {
            logger.trace("This group not found");
            return "redirect:/main";
        }
        Group group = groupRepo.findById(id).get();
        Iterable<Person> persons = personRepo.findByGroup(group);
        Iterable<Track> tracks = trackRepo.findByGroup(group);
        Iterable<Concert> concerts = concertRepo.findByGroup(group);
        model.addAttribute("group", group);
        model.addAttribute("persons", persons);
        model.addAttribute("tracks", tracks);
        model.addAttribute("concerts", concerts);
        return "group-info";
    }



    @GetMapping("/export")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        logger.trace("exportToPdf method accessed");

        response.setContentType("application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=groups" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);

        Iterable<Group> groups = groupRepo.findAll();

        GroupPDFExporter exporter= new GroupPDFExporter(groups);
        exporter.export(response);
    }


}
