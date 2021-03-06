package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.CourseOffering;
import com.example.demo.domain.Enrolment;
import com.example.demo.domain.Student;
import com.example.demo.repositories.EnrolmentRepo;

import java.util.ArrayList;
import java.util.List;


@Service
public class EnrolmentService {

    private final EnrolmentRepo enrolmentRepo;

    @Autowired
    public EnrolmentService(EnrolmentRepo enrolmentRepo) {
        this.enrolmentRepo = enrolmentRepo;
    }

    public List<Enrolment> findAllByResult(String res) {
        return enrolmentRepo.findAllByResult(res);
    }

    public boolean existRNF() {
        List<Enrolment> list = new ArrayList<>();// findAllByResult("");
        for (Enrolment e : enrolmentRepo.findAll()) {
            if (e.getResult() == null || e.getResult().equals("")) {
                list.add(e);
            }
        }
        return list != null && !list.isEmpty();
    }

    public Enrolment enrolCourse(Student student, CourseOffering offering) {
        Enrolment enrolment = new Enrolment(student, offering);
        return save(enrolment);
    }

    public Enrolment save(Enrolment enrolment) {
        Student stu = enrolment.getStudent();
        CourseOffering offering = enrolment.getOffering();
        Enrolment enrol = enrolmentRepo.findEnrolmentByStudentAndOffering(stu, offering);
        if (enrol != null) {
            enrolment.setEnrolId(enrol.getEnrolId());
        }
        return enrolmentRepo.save(enrolment);
    }

    public void saveAll(List<Enrolment> enrolments) {
        enrolmentRepo.saveAll(enrolments);
    }

    public List<Enrolment> getCurrEnrolments(CourseOffering offering) {
        return enrolmentRepo.findAllByOffering(offering);
    }

    public List<Enrolment> findAllByStudent(Student student) {
        return enrolmentRepo.findAllByStudent(student);
    }

    public List<CourseOffering> findOffering(Student student) {
        List<CourseOffering> offerings = new ArrayList<>();
        for (Enrolment enrolment : enrolmentRepo.findAllByStudent(student)) {
            offerings.add(enrolment.getOffering());
        }
        return offerings;
    }

    public List<Enrolment> findAllByOfferingAndStudent(CourseOffering offering, Student student) {
        return enrolmentRepo.findAllByOfferingAndStudent(offering, student);
    }
}
