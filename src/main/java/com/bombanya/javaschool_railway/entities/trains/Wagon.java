package com.bombanya.javaschool_railway.entities.trains;

import com.bombanya.javaschool_railway.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "wagon")
@Getter
@Setter
public class Wagon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wagon_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wagon_type_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private WagonType wagonType;

}
