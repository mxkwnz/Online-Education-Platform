package course;

public interface Course {
    void deliverContent();
    String getCourseName();
    String getTeacher();

    default boolean isGamified() {
        return false;
    }
}
