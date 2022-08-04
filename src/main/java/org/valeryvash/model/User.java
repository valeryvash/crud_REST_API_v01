package org.valeryvash.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(
        name = "users"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(
            name = "name",
            nullable = false,
            length = 25
    )
    private String name;
    @OneToMany(
            targetEntity = Event.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "user",
            orphanRemoval = true
    )
    private List<Event> events;

}
