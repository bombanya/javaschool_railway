package com.bombanya.javaschool_railway.entities;

import com.bombanya.javaschool_railway.entities.geography.Station;
import com.bombanya.javaschool_railway.entities.routes.Run;
import com.bombanya.javaschool_railway.entities.trains.Seat;
import com.bombanya.javaschool_railway.entities.trains.Wagon;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "run_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Run run;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wagon_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Wagon wagon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passenger_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "start_station_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Station startStation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "finish_station_id", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Station finishStation;

    @Column(name = "price", nullable = false)
    @JsonView(JacksonView.MinimalInfo.class)
    private Integer price;

    @Column(name = "start_serial", nullable = false)
    private Integer startSerial;

    @Column(name = "finish_serial", nullable = false)
    private Integer finishSerial;

}
