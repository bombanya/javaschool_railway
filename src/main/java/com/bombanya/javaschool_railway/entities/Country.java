package com.bombanya.javaschool_railway.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "country")
@Getter
@Setter
@ToString
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq")
    @SequenceGenerator(name = "country_seq",
            sequenceName = "country_country_id_seq",
            allocationSize = 1)
    @Column(name = "country_id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NaturalId
    private String name;
}
