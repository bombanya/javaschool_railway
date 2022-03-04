package com.bombanya.javaschool_railway.entities.trains;

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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wagon_type_id", nullable = false)
    private WagonType wagonType;

    @Column(name = "inside_wagon_id", nullable = false)
    private Integer insideWagonId;

    @Column(name = "class", nullable = false)
    private Integer seatClass;

    @Column(name = "power_socket", nullable = false)
    private Boolean powerSocket;

    @Column(name = "lying", nullable = false)
    private Boolean lying;

    @Column(name = "upper", nullable = false)
    private Boolean upper;

    @Column(name = "next_to_table", nullable = false)
    private Boolean nextToTable;
}
