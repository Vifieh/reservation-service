package com.reservation.reservationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomName extends BaseEntity{

    @Id
    private String id;
    private String name;

    @OneToOne(mappedBy = "roomName", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Room room;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;
}
