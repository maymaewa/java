package com.oop;

import com.oop.repos.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class GreetingController {
    @Autowired
    private GroupRepo groupRepo;

    @GetMapping
    public String main(Model model){
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("groups", groups);
        return "main";
    }



    @PostMapping
    public String add(@RequestParam String name, @RequestParam int year, @RequestParam int rating,@RequestParam int tickets,
                      @RequestParam int countOfConcerts, Model model){
        Group group = new Group(name, year, rating, tickets, countOfConcerts);
        groupRepo.save(group);

        Iterable<Group> groups = groupRepo.findAll();
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
        Optional<Group> group = groupRepo.findById(id);
        ArrayList<Group> res = new ArrayList<>();
        group.ifPresent(res::add);
        model.addAttribute("groups", res);
        return "group-edit";
    }
    @PostMapping("/{id}edit")
    public String groupUpdate(@PathVariable(value = "id") Group group, @RequestParam String name, @RequestParam int year, @RequestParam int rating, @RequestParam int tickets,
                      @RequestParam int countOfConcerts, Model model){
        //Optional<Group> group = groupRepo.findById(id);
        group.setName(name);
        group.setYear(year);
        group.setRating(rating);
        group.setTickets(tickets);
        group.setCountOfConcert(countOfConcerts);
        //model.addAttribute("groups", groups);
        groupRepo.save(group);


        return "redirect:";
    }
    @PostMapping("/{id}remove")
    public String groupDelete(@PathVariable(value = "id") Group group, Model model){
        groupRepo.delete(group);
        return "redirect:";
    }




}
