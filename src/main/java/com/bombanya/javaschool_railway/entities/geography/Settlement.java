package com.bombanya.javaschool_railway.entities.geography;

import com.bombanya.javaschool_railway.JacksonView;
import com.bombanya.javaschool_railway.entities.customTypes.ZoneIdType;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.ZoneId;

@Entity
@TypeDef(
        name = "zoneid",
        defaultForType = ZoneId.class,
        typeClass = ZoneIdType.class
)
@Table(name = "settlement")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Integer id;

    @Column(name = "name", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private Region region;

    @Column(name = "time_zone", nullable = false)
    @JsonView(JacksonView.UserInfo.class)
    private ZoneId timeZone;
}
