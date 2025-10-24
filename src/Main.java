import dao.StudentDAO;
import dao.TeacherDAO;
import dao.SubjectDAO;
import model.Student;
import model.Teacher;
import course.facade.StudentPortalFacade;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TeacherDAO teacherDAO = new TeacherDAO();
        StudentDAO studentDAO = new StudentDAO();

        List<Teacher> teachers = teacherDAO.getAllTeachers();

        System.out.println("---Online Education Platform---");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Student student = studentDAO.getStudent(username, password);
        if (student == null) {
            System.out.println("No existing account found. Creating new...");
            int courseNumber = 0;
            while (courseNumber < 1 || courseNumber > 3) {
                System.out.print("Enter your course number (1, 2, or 3): ");
                try {
                    courseNumber = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
            }
            student = new Student(username, courseNumber);
            studentDAO.insertStudent(student, password);
        }

        List<Student> allStudents = studentDAO.getAllStudents();
        System.out.println("\nWelcome " + student.getUsername() + "!");

        StudentPortalFacade portal = new StudentPortalFacade(allStudents, teachers);
        portal.startPortal(student);
        studentDAO.updatePoints(student);
    }
}
