package guru.qa.country.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.country.data.graphql.CountryGql;

import java.util.UUID;

public record Country(@JsonProperty("id")
                      UUID id,
                      @JsonProperty("country_name")
                      String countryName,
                      @JsonProperty("country_code")
                      String countryCode) {

    public static Country fromEntity(CountryEntity ce) {
        return new Country(
                ce.getId(),
                ce.getCountryName(),
                ce.getCountryCode());
    }

    public static Country fromGqlCountry(CountryGql countryGql) {
        return new Country(
                countryGql.id(),
                countryGql.countryName(),
                countryGql.countryCode()
        );
    }
}

