package com.bombanya.javaschool_railway.entities.trains;

import com.bombanya.javaschool_railway.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NaturalId
    @JsonView(JacksonView.UserInfo.class)
    private String name;

    @Column(name = "toilets", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer toilets;

}
