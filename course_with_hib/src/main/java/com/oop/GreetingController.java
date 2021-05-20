package com.oop;

import com.oop.repos.ConcertRepo;
import com.oop.repos.GroupRepo;
import com.oop.repos.PersonRepo;
import com.oop.repos.TrackRepo;
import org.apache.catalina.filters.ExpiresFilter;
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

    @GetMapping
    public String main(Model model){
        Iterable<Group> groups = groupRepo.findByOrderByRatingAsc();
        model.addAttribute("groups", groups);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model){
        Iterable<Group> groups;
        if (filter != null && !filter.isEmpty()){
            groups = groupRepo.findByName(filter);
        }
        else{
            groups = groupRepo.findAll();
        }
        model.addAttribute("groups", groups);
        return "main";
    }


    @GetMapping("/{id}edit")
    public String groupEdit(@PathVariable(value = "id") int id, Model model){
        if(!groupRepo.existsById(id)) {
            return "redirect:/main";
        }
        Group group = groupRepo.findById(id).get();
        model.addAttribute("group", group);
        return "group-new";
    }


    @GetMapping("/new")
    public String addNewGroup(Model model){
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("group", new Group());

        return "group-new";
    }

    @PostMapping("/save")
    public String saveGroup(@Valid Group group, BindingResult bindingResult, Model model){
        if( bindingResult.hasErrors()) {
            model.addAttribute("group", group);
            return "group-new";
        }
        groupRepo.save(group);

        return "redirect:/";
    }


    @PostMapping("/{id}remove")
    public String groupDelete(@PathVariable(value = "id") Group group, Model model){
        Iterable<Person> persons = personRepo.findByGroup(group);
        for(Person p : persons){
            personRepo.delete(p);
        }

        Iterable<Track> tracks = trackRepo.findByGroup(group);
        for(Track p : tracks){
            trackRepo.delete(p);
        }

        Iterable<Concert> concerts = concertRepo.findByGroup(group);
        for(Concert p : concerts){
            concertRepo.delete(p);
        }

        groupRepo.delete(group);
        return "redirect:";
    }

    @GetMapping("/export")
    public void exportToPdf(HttpServletResponse response) throws IOException {
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
