package com.bombanya.javaschool_railway.entities.routes;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class RunUpdateId implements Serializable {
    private static final long serialVersionUID = -8780621931056471590L;

    @Column(name = "run_id", nullable = false)
    private Integer runId;

    @Column(name = "station_id", nullable = false)
    private Integer stationId;

    @Override
    public int hashCode() {
        return Objects.hash(runId, stationId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RunUpdateId entity = (RunUpdateId) o;
        return Objects.equals(this.runId, entity.runId) &&
                Objects.equals(this.stationId, entity.stationId);
    }
}