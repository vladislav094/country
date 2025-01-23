package guru.qa.country.data.graphql;

import guru.qa.country.data.CountryEntity;

public record CountryInputGql(String countryName,
                              String countryCode) {

    public static CountryInputGql fromEntity(CountryEntity ce) {
        return new CountryInputGql(ce.getCountryName(), ce.getCountryName());
    }
}
