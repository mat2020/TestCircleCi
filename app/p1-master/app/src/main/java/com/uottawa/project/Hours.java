package com.uottawa.project;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;


public class Hours {



    private String id;

    private List<SingleHour> specialHours = new ArrayList<>();;

    private List<SingleHour> weeklyHours = new ArrayList<>();;

    private String name;

    public Hours() {

    }

    public Hours(String name) {
        this.name = name;
        this.specialHours = new ArrayList<>();
        this.weeklyHours = new ArrayList<>();

        this.weeklyHours.add(new SingleHour(0, -1, -1));

        for (int i = 1; i < 6; i++) {
            this.weeklyHours.add(new SingleHour(0, 8, 17));
        }
        this.weeklyHours.add(new SingleHour(0, -1, -1));
    }

    /*
     * Returns an array with the start and end hours given the date in a long
     * of milliseconds since January 1 1970. Returns null if the not working.
     */
    @Exclude
    public int[] getHours(long date) {
        //can't compare longs for specific days because the numbers change for some reason..
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
        for (int i = 0; i < this.specialHours.size(); i++) {
            if (df.format(date).equals(df.format(this.specialHours.get(i).day))) {
                int[] toReturn = {this.specialHours.get(i).start, this.specialHours.get(i).end};
                if (toReturn[0] == -1 || toReturn[1] == -1) {
                    return null;
                }
                return toReturn;
            }
        }

        String fullDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
        int day = 0;

        if (fullDate.contains("Monday")) {
            day = 1;
        } else if (fullDate.contains("Tuesday")) {
            day = 2;
        } else if (fullDate.contains("Wednesday")) {
            day = 3;
        } else if (fullDate.contains("Thursday")) {
            day = 4;
        } else if (fullDate.contains("Friday")) {
            day = 5;
        } else if (fullDate.contains("Saturday")) {
            day = 6;
        }

        int[] toReturn = {this.weeklyHours.get(day).start, this.weeklyHours.get(day).end};
        if (toReturn[0] == -1 || toReturn[1] == -1) {
            return null;
        }
        return toReturn;
    }

    public void setHours(long date, int start, int end, boolean repeat) {
        if (repeat) {
            String fullDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
            int day = 0;

            if (fullDate.contains("Monday")) {
                day = 1;
            } else if (fullDate.contains("Tuesday")) {
                day = 2;
            } else if (fullDate.contains("Wednesday")) {
                day = 3;
            } else if (fullDate.contains("Thursday")) {
                day = 4;
            } else if (fullDate.contains("Friday")) {
                day = 5;
            } else if (fullDate.contains("Saturday")) {
                day = 6;
            }

            this.weeklyHours.get(day).start = start;
            this.weeklyHours.get(day).end = end;

        } else {
            //look for special hour first
            for (int i = 0; i < this.specialHours.size(); i++) {
                //can't compare longs because the numbers change for some reason..
                DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
                if (df.format(date).equals(df.format(this.specialHours.get(i).day))) {
                    this.specialHours.get(i).start = start;
                    this.specialHours.get(i).end = end;
                    return;
                }
            }
            //if not found, add new one
            this.specialHours.add(new SingleHour(date, start, end));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SingleHour> getSpecialHours() {
        return specialHours;
    }

    public void setSpecialHours(List<SingleHour> specialHours) {
        this.specialHours = specialHours;
    }

    public List<SingleHour> getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(List<SingleHour> weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
