package com.bombanya.javaschool_railway.entities.trains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "wagon_type")
@Getter
@Setter
@ToString
public class WagonType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wagon_type_seq")
    @SequenceGenerator(name = "wagon_type_seq",
            sequenceName = "wagon_type_wagon_type_id_seq",
            allocationSize = 1)
    @Column(name = "wagon_type_id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NaturalId
    private String name;

    @Column(name = "toilets", nullable = false)
    private Integer toilets;

}
