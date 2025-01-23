package guru.qa.country.service;

import guru.qa.country.data.Country;
import guru.qa.country.data.CountryEntity;
import guru.qa.country.data.CountryRepository;
import guru.qa.country.data.graphql.CountryGql;
import guru.qa.country.data.graphql.CountryInputGql;
import guru.qa.country.exception.CountryNotFoundException;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DbCountryService implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country countryById(String id) {
        return Country.fromGqlCountry(countryGqlById(id));
    }

    @Override
    public List<Country> allCountries() {
        return countryRepository.findAll()
                .stream()
                .map(Country::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public @Nonnull Country addCountry(@Nonnull Country country) {
        final String countryName = country.countryName();
        final String countryCode = country.countryCode();

        CountryEntity ce = new CountryEntity();
        ce.setCountryName(countryName);
        ce.setCountryCode(countryCode);

        return Country.fromEntity(countryRepository.save(ce));
    }

    @Deprecated
    @Transactional
    public @Nonnull Country updateCountryName(@Nonnull String currentCountryName, @Nonnull String newCountryName) {
        CountryEntity country = countryRepository.findByCountryName(currentCountryName);
        if (country == null) {
            throw new IllegalArgumentException(String.format("Country with name %s not found", currentCountryName));
        }
        country.setCountryName(newCountryName);

        return Country.fromEntity(countryRepository.save(country));
    }

    @Override
    @Transactional
    public Country updateCountryById(Country country) {
        CountryEntity categoryEntity = countryRepository.findById(country.id()).orElseGet(
                () -> {
                    CountryEntity ce = new CountryEntity();
                    ce.setCountryName(country.countryName());
                    ce.setCountryCode(country.countryCode());
                    return ce;
                });
        CountryEntity saved = countryRepository.save(categoryEntity);
        return Country.fromEntity(saved);
    }

    @Override
    public CountryGql countryGqlById(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .map(ce -> new CountryGql(
                        ce.getId(),
                        ce.getCountryName(),
                        ce.getCountryCode()
                )).orElseThrow(CountryNotFoundException::new);
    }

    @Override
    public Slice<CountryGql> allGqlCountries(Pageable pageable) {
        return countryRepository.findAll(pageable)
                .map(ce -> new CountryGql(
                        ce.getId(),
                        ce.getCountryName(),
                        ce.getCountryCode()
                ));
    }

    @Override
    public CountryGql addCountryGql(CountryInputGql country) {
        CountryEntity ce = new CountryEntity();
        ce.setCountryName(country.countryName());
        ce.setCountryCode(country.countryCode());
        CountryEntity saved = countryRepository.save(ce);
        return new CountryGql(
                saved.getId(),
                saved.getCountryName(),
                saved.getCountryCode()
        );
    }
}
