package guru.qa.country.data.graphql;

import guru.qa.country.data.CountryEntity;

import java.util.UUID;

public record CountryGql(UUID id,
                         String countryName,
                         String countryCode) {

    public static CountryGql fromEntity(CountryEntity ce) {
        return new CountryGql(ce.getId(), ce.getCountryName(), ce.getCountryCode());
    }
}
