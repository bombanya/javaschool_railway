package com.bombanya.javaschool_railway.entities;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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
    @JsonView(JacksonView.MinimalInfo.class)
    private String name;

    @Column(name = "surname", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private String surname;

    @Column(name = "patronymic")
    @JsonView(JacksonView.MinimalInfo.class)
    private String patronymic;

    @Column(name = "birth_date", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private LocalDate birthDate;

    @Column(name = "passport_id", nullable = false)
    @NaturalId
    @JsonView(JacksonView.MinimalInfo.class)
    private String passportId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(name, passenger.name)
                && Objects.equals(surname, passenger.surname)
                && Objects.equals(patronymic, passenger.patronymic)
                && Objects.equals(birthDate, passenger.birthDate)
                && Objects.equals(passportId, passenger.passportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, patronymic, birthDate, passportId);
    }
}
