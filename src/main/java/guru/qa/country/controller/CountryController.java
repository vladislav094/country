package guru.qa.country.controller;

import guru.qa.country.data.Country;
import guru.qa.country.service.DbCountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    private static final Logger LOG = LoggerFactory.getLogger(CountryController.class);
    private final DbCountryService countryService;

    @Autowired
    public CountryController(DbCountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public List<Country> getAll() {
        return countryService.allCountries();
    }

    @GetMapping("/{id}")
    public Country byId(@PathVariable("id") String id) {
        return countryService.countryById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Country addCountry(@RequestBody Country country) {
        return countryService.addCountry(country);
    }
}
