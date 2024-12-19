package guru.qa.country.service;

import guru.qa.country.data.Country;
import guru.qa.country.data.CountryEntity;
import guru.qa.country.data.CountryRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(Country::fromEntity)
                .toList();
    }

    public Country getCountryByName(@Nonnull String countryName) {
        return Country.fromEntity(
                countryRepository.findByCountryName(countryName)
        );
    }

    @Transactional
    public @Nonnull Country saveCountry(@Nonnull Country country) {
        final String countryName = country.countryName();
        final String countryCode = country.countryCode();

        CountryEntity ce = new CountryEntity();
        ce.setCountryName(countryName);
        ce.setCountryCode(countryCode);

        return Country.fromEntity(countryRepository.save(ce));
    }

    @Transactional
    public @Nonnull Country updateCountryName(@Nonnull String currentCountryName, @Nonnull String newCountryName) {
        CountryEntity country = countryRepository.findByCountryName(currentCountryName);
        if (country == null) {
            throw new IllegalArgumentException(String.format("Country with name %s not found", currentCountryName));
        }
        country.setCountryName(newCountryName);

        return Country.fromEntity(countryRepository.save(country));
    }
}
