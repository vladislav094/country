package guru.qa.country.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record Country(@JsonProperty("country_name")
                      String countryName,
                      @JsonProperty("country_code")
                      String countryCode,
                      @JsonProperty("date")
                      Date independencyDate) {
}