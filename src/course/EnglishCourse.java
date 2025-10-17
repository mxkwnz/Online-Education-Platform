package course;

public class EnglishCourse implements Course {
    private final String teacher;

    public EnglishCourse(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public void deliverContent(){
        System.out.println("Staring to learn English.");
    }

    @Override
    public String getCourseName() {
        return "English";
    }

    @Override
    public String getTeacher() {
        return teacher;
    }
}
