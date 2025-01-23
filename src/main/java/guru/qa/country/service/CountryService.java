package guru.qa.country.service;

import guru.qa.country.data.Country;
import guru.qa.country.data.graphql.CountryGql;
import guru.qa.country.data.graphql.CountryInputGql;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CountryService {

    Country countryById(String id);

    List<Country> allCountries();

    Country addCountry(Country country);

    Country updateCountryById(Country country);

    CountryGql countryGqlById(String id);

    Slice<CountryGql> allGqlCountries(Pageable pageable);

    CountryGql addCountryGql(CountryInputGql country);
}
