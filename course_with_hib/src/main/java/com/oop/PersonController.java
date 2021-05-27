package com.oop;

import com.oop.repos.GroupRepo;
import com.oop.repos.PersonRepo;
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
public class PersonController {
    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private GroupRepo groupRepo;

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping("/persons")
    public String main(Model model){
        logger.trace("Main method accessed");

        Iterable<Group> groups = groupRepo.findAll();
        Iterable<Person> persons = personRepo.findAll();
        model.addAttribute("persons", persons);
        model.addAttribute("groups", groups);
        return "persons";
    }

    @GetMapping("/persons/new")
    public String addNewPerson(Model model){
        logger.trace("addNewPerson method accessed");
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("person", new Person());
        model.addAttribute("groups", groups);

        return "person-new";
    }

    @PostMapping("/persons/save")
    public String savePerson(@Valid Person person, BindingResult bindingResult, Model model){
        logger.trace("savePerson method accessed");
        if( bindingResult.hasErrors()) {
            logger.error("Errors found");
            Iterable<Group> groups = groupRepo.findAll();
            model.addAttribute("person", person);
            model.addAttribute("groups", groups);
            return "person-new";
        }
        personRepo.save(person);

        return "redirect:/persons";
    }

    @PostMapping("filterPerson")
    public String filterPerson(@RequestParam String filter, Model model){
        logger.trace("filterPerson method accessed");
        Iterable<Person> persons;
        if (filter != null && !filter.isEmpty()){
            logger.trace("Found");
            persons = personRepo.findByName(filter);

        }
        else{
            logger.trace("Not found");
            persons = personRepo.findAll();
        }
        model.addAttribute("persons", persons);
        return "persons";
    }

    @GetMapping("/persons/{id}edit")
    public String personEdit(@PathVariable(value = "id") int id, Model model){
        logger.trace("personEdit method accessed");
        if(!personRepo.existsById(id)) {
            logger.trace("This person not found");
            return "redirect:/persons";
        }
        Person person = personRepo.findById(id).get();
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("person", person);
        model.addAttribute("groups", groups);
        return "person-new";
    }

    @GetMapping("/persons/{id}delete")
    public String personDelete(@PathVariable(value = "id") int id, Model model){

        logger.trace("personDelete method accessed");
        personRepo.deleteById(id);

        return "redirect:/persons";
    }

}
