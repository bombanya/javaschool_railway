package com.bombanya.javaschool_railway.entities.routes;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "run")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "run_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "start_utc", nullable = false)
    private Instant startUtc;

    @Column(name = "finish_utc", nullable = false)
    private Instant finishUtc;
}
