package vttp.iss.day17lecture.controllers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.iss.day17lecture.model.Country;
import vttp.iss.day17lecture.services.ProcessService;

@Controller
@RequestMapping(path = "/home")
public class HomeController {

    @Autowired
    ProcessService processSvc;

    @GetMapping(path = "/booksearch")
    public String bookSearchForm() {

        return "booksearch";
    }

    @GetMapping(path = "/countries")
    public ResponseEntity<String> listCountries() {

        ResponseEntity<String> result = processSvc.getCountries();
        
        return result;
    }

    // Transforming String into a JsonObject
    @GetMapping(path = "/countries/list")
    public String listCountries2(Model model) {

        ResponseEntity<String> result = processSvc.getCountries();
        
        String jsonString = result.getBody().toString();

        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();
        // System.out.printf(">>> JsonObject: %s\n", jsonObject);

        JsonObject jsonObjectData = jsonObject.getJsonObject("data");
        System.out.printf(">>> jsonObjectData: %s\n", jsonObjectData);
        System.out.printf(">>> jsonObjectData Size: %s\n", jsonObjectData.size());

        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();
        List<Country> countries = new ArrayList<>();

        for (Entry<String, JsonValue> entry : entries) {
            // System.out.printf(">>> %s: %s\n", entry.getKey(), entry.getValue().toString());
            // System.out.printf(">>> %s: %s\n", entry.getKey(), entry.getValue().asJsonObject().getString("country"));

            countries.add(new Country(entry.getKey(), entry.getValue().asJsonObject().getString("country")));
        }

        model.addAttribute("countries", countries);

        return "countrylist";
    }

    @GetMapping(path = "/countrysearch")
    public String countriesSearchForm() {

        return "countrysearch";
    }

    @GetMapping(path = "/searchregion")
    public String countriesSearchRegionForm(Model model) {

        ResponseEntity<String> result = processSvc.getCountries();
        
        String jsonString = result.getBody().toString();

        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();

        JsonObject jsonObjectData = jsonObject.getJsonObject("data");

        Set<Entry<String, JsonValue>> entries = jsonObjectData.entrySet();
        Set<String> regions = new HashSet<>();

        for (Entry<String, JsonValue> entry : entries) {
            regions.add(entry.getValue().asJsonObject().getString("region"));
        }

        model.addAttribute("regions", regions);

        return "searchregion";
    }
}
