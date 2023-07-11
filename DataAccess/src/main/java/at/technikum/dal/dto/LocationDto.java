package at.technikum.dal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {
    String street;
    String adminArea5; // city
    String postalCode;
    String adminArea1; // countryCode
    CoordinatesDto latLng;

    @Override
    public String toString() {
        if (postalCode.isEmpty()) {
            return adminArea5 + ", " + adminArea1;
        } else if (street.isEmpty()) {
            return postalCode + " " + adminArea5 + ", " + adminArea1;
        } else {
            return street + ", " + postalCode + " " + adminArea5 + ", " + adminArea1;
        }
    }
}
