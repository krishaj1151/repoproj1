package com.example.demo.domain;

import com.example.demo.domain.Enrolment;
import com.example.demo.domain.Schedule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "student")
public class Student extends User {

    @Column(name = "maxLoad")
    private int maxLoad = 1;
    @Column(name = "maxElectives")
    private int maxElectives = 1;
    //    @OneToMany(mappedBy = "enrolId", cascade = CascadeType.ALL)
    @Transient
    private List<Enrolment> performance = new ArrayList<>();

    public Student(String userId, String pwd) {
        super(userId, pwd);
    }

    public Student() {

    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public int getMaxElectives() {
        return maxElectives;
    }

    public void setMaxElectives(int maxElectives) {
        this.maxElectives = maxElectives;
    }

    public List<Enrolment> getPerformance() {
        return performance;
    }

    public void setPerformance(List<Enrolment> performance) {
        this.performance = performance;
    }

    public void addEnrolments(Enrolment... enrolments) {
        for (Enrolment enrolment : enrolments) {
            if (!enrolment.getStudent().equals(this)) {
                System.err.println("This enrolment does not belong to this student.");
                System.err.printf("Enrolment Detail:\nCourseOffering: %s\nStudent: %s\n",
                        enrolment.getOffering(), enrolment.getStudent().getName());
                continue;
            }
            if (!enrolment.getOffering().getSchedule().equals(Schedule.currentSchedule())) {
                System.err.println("Cannot enrol courses not this semester.");
                System.err.printf("Enrolment Detail:\nCourseOffering: %s\nStudent: %s\n",
                        enrolment.getOffering(), enrolment.getStudent().getName());
                continue;
            }
            performance.add(enrolment);
        }
    }
}
