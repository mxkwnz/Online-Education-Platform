package course.facade;

import course.Course;
import course.builder.CourseBuilder;
import model.Student;
import model.Teacher;

import java.util.List;
import java.util.Scanner;

public class StudentPortalFacade {
    private Scanner scanner = new Scanner(System.in);
    private List<Student> allStudents;
    private List<Teacher> allTeachers;

    public StudentPortalFacade(List<Student> students, List<Teacher> teachers) {
        this.allStudents = students;
        this.allTeachers = teachers;
    }

    public void startPortal(Student student) {
        while (true) {
            System.out.println("\n---Student Portal---");
            System.out.println("1. View My Courses");
            System.out.println("2. Enroll in a course");
            System.out.println("3. Exit from course");
            System.out.println("4. Start Learning");
            System.out.println("5. Show My Points");
            System.out.println("6. Show Leaderboard");
            System.out.println("7. View All Teachers");
            System.out.println("8. Logout");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> student.showCourses();
                case 2 -> enrollCourse(student);
                case 3 -> exitCourse(student);
                case 4 -> startLearning(student);
                case 5 -> System.out.println("Your Total Points: " + student.getPoints());
                case 6 -> showLeaderboard();
                case 7 -> showTeachers();
                case 8 -> { System.out.println("Logged out."); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void enrollCourse(Student student) {
        System.out.println("\nAvailable courses:");
        System.out.println("1. Mathematics");
        System.out.println("2. Programming");
        System.out.println("3. History");
        System.out.println("4. English");
        System.out.print("Choose course: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String type=switch (choice){
            case 1 -> "Mathematics";
            case 2 -> "Programming";
            case 3 -> "History";
            case 4 -> "English";
            default -> "";
        };

        System.out.println("\nAvailable teachers for this subject:");
        allTeachers.stream()
                .filter(t -> t.getSubject().equalsIgnoreCase(type.toLowerCase()))
                .forEach(t -> System.out.println("- " + t.getName()));

        System.out.print("Enter teacher name: ");
        String teacherName = scanner.nextLine();
        Teacher chosen = allTeachers.stream()
                .filter(t -> t.getName().equalsIgnoreCase(teacherName))
                .findFirst().orElse(null);

        if (chosen == null) {
            System.out.println("Teacher not found.");
            return;
        }

        Course course = new CourseBuilder.Builder()
                .setType(type)
                .setTeacher(chosen)
                .setMentor(promptYesNo("Add Mentor Support?"))
                .setCertificate(promptYesNo("Add Certificate?"))
                .setGamification(promptYesNo("Add Gamification?"))
                .build();

        if(student.isEnrolled(course.getCourseName())){
            System.out.println("You have already enrolled to the course.");
            return;
        }

        student.addCourse(course);
        System.out.println("You have successfully enrolled to the course: " + course.getCourseName() + " (Teacher: " + course.getTeacher() + ")");
    }

    private boolean promptYesNo(String question) {
        System.out.print(question + "(Y/N): ");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    private void exitCourse(Student student) {
        System.out.println("\nEnter course name to exit: ");
        String name = scanner.nextLine();
        if(student.isEnrolled(name)){
            student.removeCourse(name);
            System.out.println("You have successfully exited from the course " + name);
        }
        else{
            System.out.println("You have not enrolled in that course");
        }
    }

    private void startLearning(Student student) {
        System.out.println("\nEnter course name to start learning: ");
        String name = scanner.nextLine();
        if (!student.isEnrolled(name)){
            System.out.println("You have not enrolled in that course");
            return;
        }

        Course course = student.getCourse(name);
        System.out.println("---Learning session---");
        System.out.println("Course: " + course.getCourseName());
        System.out.println("Teacher: " + course.getTeacher());

        course.deliverContent();

        System.out.println("\nStart lesson:");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Choose: ");
        String input = scanner.nextLine().trim();

        if (!input.equals("1")) {
            System.out.println("Lesson canceled.");
            return;
        }

        if (course.isGamified()) {
            System.out.println("\nGood job! You have earned 30 points for your work today. Keep it up!");
            student.addPoints(30);
            System.out.println("Total Points: " + student.getPoints());
        } else {
            System.out.println("Lesson completed successfully.");
        }
    }

    private void showLeaderboard() {
        System.out.println("\nLeaderboard:");
        allStudents.stream()
                .sorted((a, b) -> b.getPoints() - a.getPoints())
                .forEach(s -> System.out.println(s.getUsername() + " — " + s.getPoints() + " pts"));
    }

    private void showTeachers() {
        System.out.println("\nList of Teachers:");
        allTeachers.forEach(t -> System.out.println("- " + t));
    }
}
