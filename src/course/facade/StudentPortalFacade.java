package course.facade;

import course.Course;
import course.builder.CourseBuilder;
import course.decorator.MentorSupportDecorator;
import dao.MessageDAO;
import dao.SubjectDAO;
import model.Message;
import model.Student;
import model.Teacher;

import java.util.List;
import java.util.Scanner;

public class StudentPortalFacade {
    private Scanner scanner = new Scanner(System.in);
    private List<Student> allStudents;
    private List<Teacher> allTeachers;
    private SubjectDAO subjectDAO = new SubjectDAO();
    private MessageDAO messageDAO = new MessageDAO();

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
            System.out.println("8. Message Mentor");
            System.out.println("9. View My Messages");
            System.out.println("10. Logout");
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
                case 8 -> messageMentor(student);
                case 9 -> student.showMessages();
                case 10 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void enrollCourse(Student student) {
        System.out.println("\nAvailable courses for Course " + student.getCourseNumber() + ":");

        List<String> availableSubjects = subjectDAO.getSubjectsByCourse(student.getCourseNumber());

        if (availableSubjects.isEmpty()) {
            System.out.println("No subjects found for this course in the database.");
            return;
        }

        for (int i = 0; i < availableSubjects.size(); i++) {
            System.out.println((i + 1) + ". " + availableSubjects.get(i));
        }

        System.out.print("Choose course: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > availableSubjects.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String type = availableSubjects.get(choice - 1);

        System.out.println("\nAvailable teachers for this subject:");
        allTeachers.stream()
                .filter(t -> t.getSubject().equalsIgnoreCase(type))
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

        if (student.isEnrolled(course.getCourseName())) {
            System.out.println("You have already enrolled to this course.");
            return;
        }

        student.addCourse(course);
        System.out.println("Successfully enrolled: " + course.getCourseName() + " (Teacher: " + course.getTeacher() + ")");
    }

    private boolean promptYesNo(String question) {
        System.out.print(question + " (Y/N): ");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    private void exitCourse(Student student) {
        System.out.print("\nEnter course name to exit: ");
        String name = scanner.nextLine();
        if (student.isEnrolled(name)) {
            student.removeCourse(name);
            System.out.println("Successfully exited from " + name);
        } else {
            System.out.println("You have not enrolled in that course");
        }
    }

    private void startLearning(Student student) {
        System.out.print("\nEnter course name to start learning: ");
        String name = scanner.nextLine();
        if (!student.isEnrolled(name)) {
            System.out.println("You have not enrolled in that course");
            return;
        }

        Course course = student.getCourse(name);
        System.out.println("---Learning session---");
        System.out.println("Course: " + course.getCourseName());
        System.out.println("Teacher: " + course.getTeacher());
        course.deliverContent();

        System.out.print("\nStart lesson? (1=Yes, 2=No): ");
        String input = scanner.nextLine().trim();

        if (!input.equals("1")) {
            System.out.println("Lesson canceled.");
            return;
        }

        if (course.isGamified()) {
            System.out.println("\nGood job! You earned 30 points!");
            student.addPoints(30);
            System.out.println("Total Points: " + student.getPoints());
        } else {
            System.out.println("Lesson completed successfully.");
        }
    }

    private void messageMentor(Student student) {
        System.out.println("\n--- Message Mentor ---");

        List<Course> coursesWithMentor = student.getEnrolledCourses().stream()
                .filter(this::hasMentorSupport)
                .toList();

        if (coursesWithMentor.isEmpty()) {
            System.out.println("No courses with mentor support. Please enroll with mentor support first.");
            return;
        }

        System.out.println("Select course:");
        for (int i = 0; i < coursesWithMentor.size(); i++) {
            Course c = coursesWithMentor.get(i);
            System.out.println((i + 1) + ". " + c.getCourseName() + " (" + c.getTeacher() + ")");
        }

        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > coursesWithMentor.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Course selectedCourse = coursesWithMentor.get(choice - 1);
        String mentorName = selectedCourse.getTeacher();

        System.out.print("Message to " + mentorName + ": ");
        String messageContent = scanner.nextLine();

        if (messageContent.trim().isEmpty()) {
            System.out.println("Message cannot be empty.");
            return;
        }

        Message message = new Message(student.getUsername(), mentorName, messageContent);
        student.addMessage(message);
        messageDAO.insertMessage(message);

        System.out.println("Message sent!");

        String mentorResponse = generateMentorResponse(messageContent);
        Message response = new Message(mentorName, student.getUsername(), mentorResponse);
        student.addMessage(response);
        messageDAO.insertMessage(response);
        System.out.println("\n[Mentor Reply]: " + mentorResponse);
    }

    private boolean hasMentorSupport(Course course) {
        return course instanceof MentorSupportDecorator ||
                (course instanceof course.decorator.CourseDecorator && checkDecoratorChain(course));
    }

    private boolean checkDecoratorChain(Course course) {
        if (course instanceof course.decorator.CourseDecorator) {
            try {
                java.lang.reflect.Field field = course.decorator.CourseDecorator.class.getDeclaredField("course");
                field.setAccessible(true);
                Course wrappedCourse = (Course) field.get(course);
                if (wrappedCourse instanceof MentorSupportDecorator) return true;
                return checkDecoratorChain(wrappedCourse);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private String generateMentorResponse(String msg) {
        return "NO.";
    }

    private void showLeaderboard() {
        System.out.println("\nLeaderboard:");
        allStudents.stream()
                .sorted((a, b) -> b.getPoints() - a.getPoints())
                .forEach(s -> System.out.println(s.getUsername() + " (Course " + s.getCourseNumber() + ") — " + s.getPoints() + " pts"));
    }

    private void showTeachers() {
        System.out.println("\nList of Teachers:");
        allTeachers.forEach(t -> System.out.println("- " + t));
    }
}
