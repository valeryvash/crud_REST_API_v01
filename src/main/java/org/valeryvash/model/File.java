package org.valeryvash.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "files"
)
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

    @OneToMany(
            targetEntity = Event.class,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "file",
            orphanRemoval = true
    )
    private List<Event> events;

    public File() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> eventList) {
        this.events = eventList;
    }
}
