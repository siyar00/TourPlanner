package at.technikum.planner.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String street;
    private Integer zip;
    private String city;
    private String country;

    @Override
    public String toString() {
        if (street == null) {
            return zip + " " + city + ", " + country;
        } else if (zip == null) {
            return street + ", " + city + ", " + country;
        } else if (city == null) {
            return street + ", " + zip + " " + country;
        } else if (country == null) {
            return street + ", " + zip + " " + city;
        } else {
            return street + ", " + zip + " " + city + ", " + country;
        }
    }
}
