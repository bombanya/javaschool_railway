package com.bombanya.javaschool_railway.entities.geography;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "settlement")
@Setter
@Getter
@ToString
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settlement_seq")
    @SequenceGenerator(name = "settlement_seq",
            sequenceName = "settlement_settlement_id_seq",
            allocationSize = 1)
    @Column(name = "settlement_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "time_zone", nullable = false)
    private String timeZone;
}
