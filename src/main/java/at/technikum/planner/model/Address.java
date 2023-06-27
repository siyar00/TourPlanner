package at.technikum.planner.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String street;
    private String city;
    private String country;
    private Integer zip;
}
