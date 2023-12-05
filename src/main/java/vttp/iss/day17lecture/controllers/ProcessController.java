package vttp.iss.day17lecture.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.iss.day17lecture.services.ProcessService;

@RestController
@RequestMapping(path = "/process")
public class ProcessController {

    @Autowired
    ProcessService processSvc;

    @PostMapping(path = "/searchBook", produces = "application/json")
    public String bookSearch(@RequestBody MultiValueMap<String, String> form) {

        String author = form.getFirst("searchName");
        System.out.printf(">>> Author: %s\n", author);

        String result = processSvc.searchBook(author);

        return result;
    }

    @PostMapping(path = "/searchCountry")
    public String countrySearch(@RequestBody MultiValueMap<String, String> form) {

        ResponseEntity<String> results = processSvc.filterCountries(form.getFirst("searchCountry"));

        return results.getBody();
    }

    @PostMapping(path = "/searchRegion")
    public String regionSearch(@RequestBody MultiValueMap<String, String> form) {

        ResponseEntity<String> results = processSvc.filterCountriesByRegion(form.getFirst("searchRegion"));

        return results.getBody();
    }
}