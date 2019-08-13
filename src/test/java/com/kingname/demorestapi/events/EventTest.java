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

    @Test
    public void testFree() {

        // Given
        Event event = Event.builder()
                        .basePrice(0)
                        .maxPrice(0)
                        .build();

        // when
        event.update();

        //Then
        assertThat(event.isFree()).isTrue();

        // Given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        // when
        event.update();

        //Then
        assertThat(event.isFree()).isFalse();

        // Given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(1000)
                .build();

        // when
        event.update();

        //Then
        assertThat(event.isFree()).isFalse();

    }

    @Test
    public void testOffline() {

        // Given
        Event event = Event.builder()
                .location("강남역 네이버 D2")
                .build();

        // when
        event.update();

        //Then
        assertThat(event.isOffline()).isTrue();

        // Given
        event = Event.builder()
                .build();

        // when
        event.update();

        //Then
        assertThat(event.isOffline()).isFalse();

    }
}
