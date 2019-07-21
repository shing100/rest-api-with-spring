package com.kingname.demorestapi.events;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class EventTest {

    @Test
    public void bulider() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        // Given
        String name = "Event";
        String dscription = "Spring";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(dscription);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(dscription);
    }
}
