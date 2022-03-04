package com.bombanya.javaschool_railway.entities.geography;

import com.bombanya.javaschool_railway.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "region")
@Setter
@Getter
@NamedEntityGraph(name = "region.fetch_country",
        includeAllAttributes = true)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @Column(name = "name", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Country country;
}
