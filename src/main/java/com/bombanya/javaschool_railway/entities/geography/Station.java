package com.bombanya.javaschool_railway.entities.geography;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "station")
@Setter
@Getter
@ToString
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "station_seq")
    @SequenceGenerator(name = "station_seq",
            sequenceName = "station_station_id_seq",
            allocationSize = 1)
    @Column(name = "station_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "settlement_id", nullable = false)
    private Settlement settlement;
}
