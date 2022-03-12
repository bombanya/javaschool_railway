package com.bombanya.javaschool_railway.entities;

import com.bombanya.javaschool_railway.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passenger")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @Column(name = "name", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private String name;

    @Column(name = "surname", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private String surname;

    @Column(name = "patronymic")
    @JsonView(JacksonView.UserInfo.class)
    private String patronymic;

    @Column(name = "birth_date", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private LocalDate birthDate;

    @Column(name = "passport_id", nullable = false)
    @NaturalId
    @JsonView(JacksonView.UserInfo.class)
    private Integer passportId;
}
