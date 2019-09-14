package com.kingname.demorestapi.events;

import com.kingname.demorestapi.accounts.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne
    private Account manager;

    public void update() {
        if (vaildFree()) {
            this.free = true;
        } else {
            this.free = false;
        }

        if (vaildOffline()) {
            this.offline = false;
        } else {
            this.offline = true;
        }
    }

    public Boolean vaildFree() {
        return this.basePrice == 0 && this.maxPrice == 0;
    }

    public Boolean vaildOffline() {
        if(this.location != null)
            System.out.println(this.location.trim().isEmpty());
        return this.location == null || this.location.trim().isEmpty();
    }
}
