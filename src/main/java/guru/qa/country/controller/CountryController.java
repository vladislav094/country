package guru.qa.country.controller;

import guru.qa.country.data.Country;
import guru.qa.country.dto.UpdateCountryRequest;
import guru.qa.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public List<Country> getAll() {
        return countryService.getAllCountries();
    }

    @GetMapping(value = "/countries", params = "country_name")
    public Country getCountryByName(@RequestParam("country_name") String countryName) {
        return countryService.getCountryByName(countryName);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Country addCountry(@RequestBody Country country) {
        return countryService.saveCountry(country);
    }

    @PatchMapping("/country/{countryName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Country updateCountryName(@PathVariable String countryName,
                                     @RequestBody UpdateCountryRequest requestData) {
        return countryService.updateCountryName(countryName, requestData.getCountryName());
    }
}
