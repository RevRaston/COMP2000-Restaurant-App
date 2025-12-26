package com.example.comp2000restaurantapp.domain.reservation;

public class Reservation {

    private final long id;
    private final String guestName;
    private final String date;
    private final String time;
    private final int partySize;
    private final String notes;
    private boolean completed;

    public Reservation(
            long id,
            String guestName,
            String date,
            String time,
            int partySize,
            String notes,
            boolean completed
    ) {
        this.id = id;
        this.guestName = guestName;
        this.date = date;
        this.time = time;
        this.partySize = partySize;
        this.notes = notes;
        this.completed = completed;
    }

    public long getId() { return id; }
    public String getGuestName() { return guestName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getPartySize() { return partySize; }
    public String getNotes() { return notes; }
    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
