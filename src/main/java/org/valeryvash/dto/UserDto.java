package org.valeryvash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.valeryvash.model.EventType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private long id;
    private String name;
    private List<EventDto> events;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDto implements Serializable {
        private long id;
        private Date timestamp;
        private EventType eventType;
        private long fileId;
        private String fileName;
        private String fileFilePath;
    }

}
