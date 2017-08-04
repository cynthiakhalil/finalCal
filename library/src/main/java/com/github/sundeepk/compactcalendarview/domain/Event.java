package com.github.sundeepk.compactcalendarview.domain;

import android.support.annotation.Nullable;

public class Event {

    private int color;
    private long timeInMillis;
    private Object data;
    int durationHour;
    int durationMin;
    private static int ID = 0;

    public Event()
    {

    }
    public Event(int color, long timeInMillis) {
        this.color = color;
        this.timeInMillis = timeInMillis;
        ID++;
    }


    public int getDurationHour() {
        return durationHour;
    }

    public void setDurationHour(int durationHour) {
        this.durationHour = durationHour;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static void setID(int ID) {
        Event.ID = ID;
    }

    public Event(int color, long timeInMillis, Object data) {
        this.color = color;
        this.timeInMillis = timeInMillis;
        this.data = data;
        ID++;
    }

    public static int getID() {
        return ID;
    }

    public int getColor() {
        return color;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (color != event.color) return false;
        if (timeInMillis != event.timeInMillis) return false;
        if (data != null ? !data.equals(event.data) : event.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = color;
        result = 31 * result + (int) (timeInMillis ^ (timeInMillis >>> 32));
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "color=" + color +
                ", timeInMillis=" + timeInMillis +
                ", data=" + data +
                '}';
    }

    public void setColor(int color) {
        this.color = color;
    }
}
