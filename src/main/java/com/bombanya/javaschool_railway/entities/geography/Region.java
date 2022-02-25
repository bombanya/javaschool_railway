package com.bombanya.javaschool_railway.entities.geography;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "region")
@Setter
@Getter
@ToString
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_seq")
    @SequenceGenerator(name = "region_seq",
            sequenceName = "region_region_id_seq",
            allocationSize = 1)
    @Column(name = "region_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
}
