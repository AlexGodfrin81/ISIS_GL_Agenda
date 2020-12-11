package agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Description : An agenda that stores events
 */
public class Agenda {
    private HashSet<Event> events = new HashSet<>();
    
    public Agenda(){}
    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Computes the events that occur on a given day
     *
     * @param day the day toi test
     * @return and iteraror to the events that occur on that day
     */
    public HashSet<Event> eventsInDay(LocalDate day) {
        HashSet<Event> ListeEvent=new HashSet();
        for(Event e:events){
            if (e.isInDay(day))
                ListeEvent.add(e);}
        return ListeEvent;
    }
    
     /**
     * Trouver les événements de l'agenda en fonction de leur titre
     * @param title le titre à rechercher
     * @return les événements qui ont le même titre
     */
    public List<Event> findByTitle(String title) {
        List<Event> ListeEvent = new ArrayList<>();
        for(Event e:events){
            if (e.getTitle() == title)
                ListeEvent.add(e);}
        return ListeEvent;
    }
    
    /**
     * Déterminer s’il y a de la place dans l'agenda pour un événement
     * @param e L'événement à tester (on se limitera aux événements simples)
     * @return vrai s’il y a de la place dans l'agenda pour cet événement
     */
    public boolean isFreeFor(Event e) {
        for (Event ev: events){
            LocalDateTime eStart = e.getStart();
            LocalDateTime evStart = ev.getStart();
            LocalDateTime eEnd = e.getStart().plus(ev.getDuration());
            LocalDateTime evEnd = ev.getStart().plus(ev.getDuration());
            if (evStart==eStart || 
                    (evStart.isBefore(eStart) && (evEnd.isAfter(eStart))) ||
                    (evStart.isBefore(eEnd) && (evEnd.isAfter(eEnd))) ||
                    (evStart.isBefore(eStart) && (evEnd.isAfter(eEnd)))){
                return false;
            }
        }
        return true;
    }
    
}
