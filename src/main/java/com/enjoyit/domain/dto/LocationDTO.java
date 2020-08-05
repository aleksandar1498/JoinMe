package com.enjoyit.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.enjoyit.enums.LocationCategory;

public class LocationDTO {
    private String id;
    @NotEmpty(message = "*City cannot be empty")
    private String city;
    @NotEmpty(message = "*Name cannot be empty")
    private String name;
    @NotNull(message = "*Location Category cannot be not set")
    private LocationCategory locationCategory;

    public LocationDTO() {
    }

    public LocationDTO(final String id) {
        this.id = id;
    }

    public LocationDTO(final String city, final String name, final LocationCategory locationCategory) {
        this.city = city;
        this.name = name;
        this.locationCategory = locationCategory;
    }

    public LocationDTO(final String id, final String city, final String name,
            final LocationCategory locationCategory) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.locationCategory = locationCategory;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public LocationCategory getLocationCategory() {
        return locationCategory;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((locationCategory == null) ? 0 : locationCategory.hashCode());
        return result;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setLocationCategory(final LocationCategory locationCategory) {
        this.locationCategory = locationCategory;
    }


}
