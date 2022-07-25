package org.valeryvash.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(
        name = "events"
)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(
            targetEntity = File.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            table = "events",
            name = "file_id",
            nullable = false
    )
    private File file;

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
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            optional = false
    )
    private User user;

    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User relatedUser) {
        this.user = relatedUser;
    }
}
