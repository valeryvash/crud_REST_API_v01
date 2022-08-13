package org.valeryvash.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "files"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "filePath",
            nullable = false
    )
    private String filePath;

    @OneToOne(
            targetEntity = Event.class,
//            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "file",
            orphanRemoval = true
    )
    private Event event;

}
