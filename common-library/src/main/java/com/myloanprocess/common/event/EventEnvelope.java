package com.myloanprocess.common.event;

import com.myloanprocess.common.constants.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEnvelope<T> {

	private String eventId;
	private EventType eventType;
	private String source;
	private LocalDateTime timestamp;
	private T payload;
}
