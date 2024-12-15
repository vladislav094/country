package guru.qa.country.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateCountryRequest {
    @JsonProperty("country_name")
    private String countryName;

    public String getCountryName() {
        return countryName;
    }
}
