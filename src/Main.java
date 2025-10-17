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

        Student student = new Student(username, password);
        allStudents.add(student);

        System.out.println("\nLogin successful! Welcome " + student.getUsername());

        StudentPortalFacade portal = new StudentPortalFacade(allStudents,teachers);
        portal.startPortal(student);
    }
}