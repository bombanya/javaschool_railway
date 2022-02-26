package com.bombanya.javaschool_railway.entities.trains;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "wagon_type")
@Getter
@Setter
public class WagonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wagon_type_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NaturalId
    private String name;

    @Column(name = "toilets", nullable = false)
    private Integer toilets;

}
