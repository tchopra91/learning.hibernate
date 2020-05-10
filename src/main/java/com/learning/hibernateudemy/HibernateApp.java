package com.learning.hibernateudemy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.learning.hibernateudemy.entity.Course;
import com.learning.hibernateudemy.entity.Review;
import com.learning.hibernateudemy.entity.Student;
import com.learning.hibernateudemy.entity.StudentProfile;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateApp {

    public static void main(String... args) {
        System.out.println("HibernateApp running...");

        SessionFactory factory = new Configuration().configure().addAnnotatedClass(Student.class)
                .addAnnotatedClass(StudentProfile.class).addAnnotatedClass(Course.class).addAnnotatedClass(Review.class)
                .buildSessionFactory();
        Logger.getLogger("org.hibernate").setLevel(Level.WARNING);

        Session session = factory.getCurrentSession();

        try {
            Student tempStudent = new Student("Tom", "Radon", "tr@cnn.com");
            StudentProfile tempStudentProfile = new StudentProfile("cold road", "KPT", "PNB");
            Course tempCourse = new Course("Spring Master");
            tempCourse.addReview(new Review("My custom comment"));

            tempStudent.setProfile(tempStudentProfile);
            tempStudent.addCourse(tempCourse);

            session.beginTransaction();

            session.save(tempCourse);
            session.save(tempStudent);

            session.getTransaction().commit();

            System.out.println("--->Student saved successfully, generated id : " + tempStudent.getId());

            session = factory.getCurrentSession();

            session.beginTransaction();

            StudentProfile savedStudentProfile = session.get(StudentProfile.class, 1);
            System.out.println("--->Saved StudentProfile :: " + savedStudentProfile);
            System.out.println("--->Saved associated Student :: " + savedStudentProfile.getStudent());
            System.out.println("--->Saved associated Courses :: " + savedStudentProfile.getStudent().getCourses());

            session.getTransaction().commit();

            // Student savedStudent = session.get(Student.class, 3);
            // savedStudent.setFirstname("Jim");
            // savedStudent.setLastname("Crazy");
            // savedStudent.setEmail("jc@ymail.com");

            // session.delete(savedStudent);

            // session.getTransaction().commit();

            // System.out.println("--->Fetched student : " + savedStudent);

            // session = factory.getCurrentSession();

            // session.beginTransaction();

            // List<Student> allStudents = session.createQuery("from
            // Student").getResultList();

            // session.getTransaction().commit();

            // System.out.println("--->All students :: " + allStudents);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}