package org.valeryvash.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.valeryvash.model.EventType;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto implements Serializable {
    private long id;
    private Date timestamp;
    private EventType eventType;
    private long fileId;
    private String fileName;
    private String fileFilePath;
    private long userId;
    private String userName;
}
