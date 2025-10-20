import course.facade.StudentPortalFacade;
import model.Student;
import model.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Teacher> teachers = new ArrayList<>(List.of(
                new Teacher("Mr.Erbol", "mathematics"),
                new Teacher("Mr.Imran", "programming"),
                new Teacher("Ms.Aizya", "history"),
                new Teacher("Ms.Aray", "english")
        ));

        List<Student> allStudents = new ArrayList<>();

        System.out.println("---Online Education Platform---");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        // Ask for course number
        int courseNumber = 0;
        while (courseNumber < 1 || courseNumber > 3) {
            System.out.print("Enter your course number (1, 2, or 3): ");
            try {
                courseNumber = Integer.parseInt(sc.nextLine());
                if (courseNumber < 1 || courseNumber > 3) {
                    System.out.println("Invalid course number. Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        Student student = new Student(username, courseNumber);
        allStudents.add(student);

        System.out.println("\nLogin successful! Welcome " + student.getUsername());
        System.out.println("Course Level: " + student.getCourseNumber());
        System.out.println("Available subjects for your course: " + student.getAvailableSubjects());

        StudentPortalFacade portal = new StudentPortalFacade(allStudents, teachers);
        portal.startPortal(student);
    }
}