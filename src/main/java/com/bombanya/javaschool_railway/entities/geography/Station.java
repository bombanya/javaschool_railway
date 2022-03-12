package com.bombanya.javaschool_railway.entities.geography;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "station")
@Setter
@Getter
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @Column(name = "name", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settlement_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Settlement settlement;
}
