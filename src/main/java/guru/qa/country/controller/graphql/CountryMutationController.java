package guru.qa.country.controller.graphql;

import guru.qa.country.data.graphql.CountryGql;
import guru.qa.country.data.graphql.CountryInputGql;
import guru.qa.country.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountryMutationController {

    private static final Logger LOG = LoggerFactory.getLogger(CountryMutationController.class);
    private final CountryService countryService;

    @Autowired
    public CountryMutationController(CountryService countryService) {
        this.countryService = countryService;
    }

    @MutationMapping
    public CountryGql addCountry(@Argument CountryInputGql input) {
        return countryService.addCountryGql(input);
    }

}
