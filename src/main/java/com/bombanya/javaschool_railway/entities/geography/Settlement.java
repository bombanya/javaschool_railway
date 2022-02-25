package com.bombanya.javaschool_railway.entities.geography;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "settlement")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "time_zone", nullable = false)
    private String timeZone;
}
