package guru.qa.country.controller.graphql;

import guru.qa.country.data.graphql.CountryGql;
import guru.qa.country.service.DbCountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountryQueryController {

    private static final Logger LOG = LoggerFactory.getLogger(CountryQueryController.class);
    private final DbCountryService countryService;

    @Autowired
    public CountryQueryController(DbCountryService countryService) {
        this.countryService = countryService;
    }

    @QueryMapping
    public Slice<CountryGql> countries(@Argument int page, @Argument int size) {
        return countryService.allGqlCountries(
                PageRequest.of(page, size)
        );
    }

    @QueryMapping
    public CountryGql country(@Argument String id) {
        return countryService.countryGqlById(id);
    }
}
