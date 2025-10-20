package model;

import course.Course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student {
    private String username;
    private String password;
    private int courseNumber; // 1, 2, or 3
    private List<Course> enrolledCourses = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private int point = 0;

    public Student(String username, String password, int courseNumber) {
        this.username = username;
        this.password = password;
        this.courseNumber = courseNumber;
    }

    public String getUsername() {
        return username;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public List<String> getAvailableSubjects() {
        return switch (courseNumber) {
            case 1 -> Arrays.asList("Mathematics", "English");
            case 2 -> Arrays.asList("Programming");
            case 3 -> Arrays.asList("History");
            default -> new ArrayList<>();
        };
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

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void showMessages() {
        if (messages.isEmpty()) {
            System.out.println("No messages.");
        } else {
            System.out.println("\n--- Your Messages ---");
            for (int i = 0; i < messages.size(); i++) {
                Message msg = messages.get(i);
                System.out.println((i + 1) + ". [" + msg.getSender() + " -> " + msg.getReceiver() + "] ");
                System.out.println("   " + msg.getContent());
                System.out.println();
            }
        }
    }
}