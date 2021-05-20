package com.oop;

import com.oop.repos.GroupRepo;
import com.oop.repos.PersonRepo;
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

    @GetMapping("/persons")
    public String main(Model model){
        Iterable<Group> groups = groupRepo.findAll();
        Iterable<Person> persons = personRepo.findAll();
        model.addAttribute("persons", persons);
        model.addAttribute("groups", groups);
        return "persons";
    }

    @GetMapping("/persons/new")
    public String addNewPerson(Model model){
        Iterable<Group> groups = groupRepo.findAll();
        model.addAttribute("person", new Person());
        model.addAttribute("groups", groups);

        return "person-new";
    }

    @PostMapping("/persons/save")
    public String savePerson(@Valid Person person, BindingResult bindingResult, Model model){
        if( bindingResult.hasErrors()) {
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
        Iterable<Person> persons;
        if (filter != null && !filter.isEmpty()){
            persons = personRepo.findByName(filter);
        }
        else{
            persons = personRepo.findAll();
        }
        model.addAttribute("persons", persons);
        return "persons";
    }

    @GetMapping("/persons/{id}edit")
    public String personEdit(@PathVariable(value = "id") int id, Model model){
        if(!personRepo.existsById(id)) {
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

        personRepo.deleteById(id);

        return "redirect:/persons";
    }

}
