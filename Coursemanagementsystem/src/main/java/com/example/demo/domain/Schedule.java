package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule implements Comparable<Schedule> {
    @Transient
    public static final int SEMESTER_COUNT = 2;
    @Transient
    public static final int WEEK_COUNT = 12;
    @Transient
    private static Schedule currSchedule = null;
    @Id
    @Column(name = "schId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schId;
    @Column(name = "year")
    private int year;
    @Column(name = "semester")
    private int semester = 1;
    @Column(name = "week")
    private int week = 1;

    public Schedule() {
    }

    public Schedule(int year, int semester) {
        this.year = year;
        this.semester = semester;
    }

    
    public static Schedule currentSchedule() {
        if (currSchedule == null) {
            throw new NullPointerException("Need to be updated from database.");
        }
        return currSchedule;
    }

    public static void setCurrentSchedule(Schedule currentSchedule) {
        currentSchedule.setSchId(1);
        Schedule.currSchedule = currentSchedule;
    }

    
    public static Schedule advance() {
        int week = currSchedule.getWeek();
        if (week <= 0)
            throw new IllegalArgumentException(String.format("Wrong Week: %s", week));
        if (week < WEEK_COUNT) {
            currSchedule.setWeek(week + 1);
        } else {
            int semester = currSchedule.getSemester();
            if (semester <= 0)
                throw new IllegalArgumentException(String.format("Wrong Semester: %s", semester));
            if (semester >= SEMESTER_COUNT) {
                int year = currSchedule.getYear();
                currSchedule.setYear(year + 1);
                currSchedule.setSemester(1);
            } else {
                currSchedule.setSemester(semester + 1);
            }
            currSchedule.setWeek(1);
        }
        return currSchedule;
    }

    public int getSchId() {
        return schId;
    }

    public void setSchId(int schId) {
        this.schId = schId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

   
    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Schedule other = (Schedule) obj;
        return this.getYear() == other.getYear() && this.getSemester() == other.getSemester();
    }

    @Override
    public int compareTo(Schedule o) {
        if (o == null) {
            return 1;
        }
        if (this.year == o.year) {
            return this.semester - o.semester;
        } else return this.year - o.year;
    }

    @Override
    public String toString() {
        return String.format("Year:%s Semester %s Week %s", year, semester, week);
    }
}
