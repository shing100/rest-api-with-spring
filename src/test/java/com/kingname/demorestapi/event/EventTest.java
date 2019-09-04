package com.kingname.demorestapi.event;

import com.kingname.demorestapi.events.Event;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(JUnitParamsRunner.class)
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
    @Parameters
    public void testFree(int basePrice, int maxPrice, Boolean isFree) {

        // Given
        Event event = Event.builder()
                        .basePrice(basePrice)
                        .maxPrice(maxPrice)
                        .build();

        // when
        event.update();

        //Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private Object[] parametersForTestFree() {
        return new Object[] {
                new Object[] {0, 0, true},
                new Object[] {100, 0, false},
                new Object[] {0, 100, false},
                new Object[] {100, 200, false}
        };
    }

    @Test
    @Parameters
    public void testOffline(String location, boolean isOffline) {

        // Given
        Event event = Event.builder()
                .location(location)
                .build();

        // when
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(isOffline);

    }

    private Object[] parametersForTestOffline() {
        return new Object[] {
                new Object[] {"강남", true},
                new Object[] {null, false},
                new Object[] {"    ", false}
        };
    }
}
