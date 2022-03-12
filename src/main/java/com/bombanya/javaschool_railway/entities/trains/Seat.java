package com.bombanya.javaschool_railway.entities.trains;

import com.bombanya.javaschool_railway.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "seat")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wagon_type_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private WagonType wagonType;

    @Column(name = "inside_wagon_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer insideWagonId;

    @Column(name = "class", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer seatClass;

    @Column(name = "power_socket", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Boolean powerSocket;

    @Column(name = "lying", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Boolean lying;

    @Column(name = "upper", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Boolean upper;

    @Column(name = "next_to_table", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Boolean nextToTable;
}
