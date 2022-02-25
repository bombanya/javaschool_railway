package com.bombanya.javaschool_railway.entities.trains;

import javax.persistence.*;

@Entity
@Table(name = "wagon")
public class Wagon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wagon_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wagon_type_id", nullable = false)
    private WagonType wagonType;

}
