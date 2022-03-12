package com.bombanya.javaschool_railway.entities.routes;

import com.bombanya.javaschool_railway.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class RouteStationId implements Serializable {
    private static final long serialVersionUID = 618081359834747982L;

    @Column(name = "route_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer routeId;

    @Column(name = "station_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer stationId;

    @Override
    public int hashCode() {
        return Objects.hash(routeId, stationId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RouteStationId entity = (RouteStationId) o;
        return Objects.equals(this.routeId, entity.routeId) &&
                Objects.equals(this.stationId, entity.stationId);
    }
}