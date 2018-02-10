package com.springbootexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springbootexample.dao.PersonRepository;
import com.springbootexample.model.Person;


@Controller
@RequestMapping(value = "/person")
public class PersonsController {
	
	@Autowired
    private PersonRepository personDao;
	
	@RequestMapping(value = "/home")
    public String dashboard(Model model) {
		model.addAttribute("view", "dashboard");
        return "/base/base";
    }
	
	@RequestMapping(value = "/appointment")
    public String appointment(Model model) {
		model.addAttribute("view", "content");
        return "/base/base";
    }
	
	@RequestMapping(value = "/services")
    public String services(Model model) {
		model.addAttribute("view", "services");
        return "/base/base";
    }
	
	@RequestMapping(value = "/staff")
    public String staff(Model model) {
		model.addAttribute("view", "staff");
        return "/base/base";
    }
	
	@RequestMapping(value = "/bill")
    public String bill(Model model) {
		model.addAttribute("view", "bill");
        return "/base/base";
    }
	
	@RequestMapping(value = "/inventory")
    public String inventory(Model model) {
		model.addAttribute("view", "inventory");
        return "/base/base";
    }
 
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam(value="id") long id, Model model) {
        try {
            Person person = new Person();
            person.setId(id);
            personDao.delete(person);
            model.addAttribute("name", "Person succesfully deleted!");
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "greeting";
    }
 
    @RequestMapping(value = "/save")
    public String create(@RequestParam(value="name", required=false, defaultValue="World") String name,@RequestParam(value="city", required=false, defaultValue="World") String city, Model model) {
        try {
            Person person = new Person();
            person.setName(name);
            person.setCity(city);
            personDao.save(person);
            model.addAttribute("name", "Person succesfully saved!");
            
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "greeting";
    }
    @RequestMapping(value = "/all")
    @ResponseBody
    public String getAllPersons() {
        try {
            return personDao.findAll().toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
