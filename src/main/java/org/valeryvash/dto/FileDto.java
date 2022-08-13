package org.valeryvash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.valeryvash.model.EventType;
import org.valeryvash.model.File;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto implements Serializable {
    private long id;
    private String name;
    private String filePath;
    private long eventId;
    private EventType eventEventType;
    private Date timestamp;
    private long eventUserId;
    private String eventUserName;
}
