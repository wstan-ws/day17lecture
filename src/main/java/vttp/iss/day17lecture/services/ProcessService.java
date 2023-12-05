package vttp.iss.day17lecture.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProcessService {

    String url_booksearch = "https://openlibrary.org/search.json?author=";
    String url_countries = "https://api.first.org/data/v1/countries";
    RestTemplate template = new RestTemplate();

    public String searchBook(String author) {

        String url = url_booksearch + author;
        String result = template.getForObject(url, String.class);

        return result;
    }

    public ResponseEntity<String> getCountries() {

        ResponseEntity<String> result = template.getForEntity(url_countries, String.class);

        return result;
    }

    public ResponseEntity<String> filterCountries(String name) {

        String url = url_countries + "?q=" + name;
        ResponseEntity<String> result = template.getForEntity(url, String.class);

        return result;
    }

    public ResponseEntity<String> filterCountriesByRegion(String region) {

        String url = url_countries + "?region=" + region;
        ResponseEntity<String> result = template.getForEntity(url, String.class);

        return result;
    }
}