/**
 *
 */
package com.enjoyit.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.enjoyit.enums.LocationCategory;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.Location;

/**
 * @author AStefanov
 */
@Entity
@Table(name = "locations")
public class JpaLocation extends BaseEntity implements Location {


    @Column
    private String address;

    @Column
    private String city;

    @Enumerated(EnumType.STRING)
    private LocationCategory locationCategory;

    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY,targetEntity = JpaEvent.class)
    private List<Event> events;

    public JpaLocation() {
    }

    public JpaLocation(final String address, final String city, final LocationCategory locationCategory) {
        this.address = address;
        this.city = city;
        this.locationCategory = locationCategory;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public List<Event> getEvents() {
        return this.events;
    }

    @Override
    public LocationCategory getLocationCategory() {
        return this.locationCategory;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setEvents(final List<Event> events) {
        this.events = events;
    }

    public void setLocationCategory(final LocationCategory locationCategory) {
        this.locationCategory = locationCategory;
    }

    @Override
    public String toString() {
        return "JpaLocation [address=" + address + ", city=" + city + ", locationCategory=" + locationCategory
                + ", events=" + events + "]";
    }

}
