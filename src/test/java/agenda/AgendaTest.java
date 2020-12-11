package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class AgendaTest {
    Agenda agenda;
    
    // November 1st, 2020
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);

    // January 5, 2021
    LocalDate jan_5_2021 = LocalDate.of(2021, 1, 5);

    // November 1st, 2020, 22:30
    LocalDateTime nov_1__2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);

    // 120 minutes
    Duration min_120 = Duration.ofMinutes(120);

    // A simple event
    // November 1st, 2020, 22:30, 120 minutes
    Event simple = new Event("Simple event", nov_1__2020_22_30, min_120);

    // A Weekly Repetitive event ending at a given date
    RepetitiveEvent fixedTermination = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, jan_5_2021);

    // A Weekly Repetitive event ending after a give number of occurrrences
    RepetitiveEvent fixedRepetitions = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, 10);
    
    // A daily repetitive event, never ending
    // November 1st, 2020, 22:30, 120 minutes
    RepetitiveEvent neverEnding = new RepetitiveEvent("Never Ending", nov_1__2020_22_30, min_120, ChronoUnit.DAYS);

    @BeforeEach
    public void setUp() {
        agenda = new Agenda();
        agenda.addEvent(simple);
        agenda.addEvent(fixedTermination);
        agenda.addEvent(fixedRepetitions);
        agenda.addEvent(neverEnding);
    }
    
    @Test
    public void testMultipleEventsInDay() {
        assertEquals(4, agenda.eventsInDay(nov_1_2020).size(), "Il y a 4 événements ce jour là");
        assertTrue(agenda.eventsInDay(nov_1_2020).contains(neverEnding));
    }
    
    @Test
    public void testFindByTitle() {
        List<Event> ListeEvent = agenda.findByTitle("Simple event");
        assertTrue(ListeEvent.size()==1);
        ListeEvent = agenda.findByTitle("viejviorjv");
        assertTrue(ListeEvent.isEmpty());
        ListeEvent = agenda.findByTitle("Fixed"); 
        // j'ai implémenté findByTitle pour qu'il donne les events avec le même nom,
        //pas lorsqu'ils contiennent le string donc size = 0 normalement.
        assertTrue(ListeEvent.isEmpty());
    }
    
    @Test
    public void testIsFreeFor(){
        assertFalse(agenda.isFreeFor(simple));
        Duration min_1 = Duration.ofMinutes(1);
        LocalDateTime nov_1__2020_12_30 = LocalDateTime.of(2020, 11, 1, 12, 30);
        Event simple1 = new Event("Simple event1", nov_1__2020_22_30, min_1);
        Event simple2 = new Event("Simple event2", nov_1__2020_12_30, min_120);
        assertFalse(agenda.isFreeFor(simple1));
        assertTrue(agenda.isFreeFor(simple2));
        agenda.addEvent(simple2);
        assertFalse(agenda.isFreeFor(simple2));
    }
}
