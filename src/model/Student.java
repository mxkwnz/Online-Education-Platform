package model;

import course.Course;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String username;
    private String password;
    private List<Course> enrolledCourses = new ArrayList<>();
    private int point = 0;

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void addCourse(Course course) {
        enrolledCourses.add(course);
    }
    public void removeCourse(String name) {
        enrolledCourses.removeIf(c -> c.getCourseName().toLowerCase().contains(name.toLowerCase()));
    }

    public boolean isEnrolled(String name) {
        for (Course c : enrolledCourses) {
            if (c.getCourseName().toLowerCase().contains(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public Course getCourse(String name) {
        for (Course c : enrolledCourses) {
            if (c.getCourseName().toLowerCase().contains(name.toLowerCase()))
                return c;
        }
        return null;
    }

    public void addPoints(int pts){
        point += pts;
    }

    public int getPoints(){
        return point;
    }

    public void showCourses(){
        if(enrolledCourses.isEmpty()){
            System.out.println("No courses enrolled");
        }
        else{
            System.out.println("Enrolled courses:");
            for (Course course : enrolledCourses) {
                System.out.println(course.getCourseName());
            }
        }
    }
}
