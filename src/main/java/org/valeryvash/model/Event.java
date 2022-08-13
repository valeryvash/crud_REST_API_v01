package org.valeryvash.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(
        name = "events"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(
            targetEntity = File.class,
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true
    )
    @JoinColumn(
            table = "events",
            name = "file_id",
            nullable = false
    )
    private File file;

    @Column(
            name = "timestamp",
            nullable = false,
            updatable = false
    )
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(
            table = "events",
            name = "event_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @ManyToOne(
            targetEntity = User.class,
//            cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            optional = false
    )
    private User user;
}
