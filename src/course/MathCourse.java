package course;

public class MathCourse implements Course {
    private final String teacher;

    public MathCourse(String teacher){
        this.teacher = teacher;
    }

    @Override
    public void deliverContent(){
        System.out.println("Starting to learn Calculus 1.");
    }

    @Override
    public String getCourseName(){
        return"Mathematics";
    }

    @Override
    public String getTeacher(){
        return teacher;
    }
}
