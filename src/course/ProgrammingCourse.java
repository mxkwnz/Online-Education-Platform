package course;

public class ProgrammingCourse implements Course {
    private final String teacher;

    public ProgrammingCourse(String teacher){
        this.teacher = teacher;
    }

    @Override
    public void deliverContent(){
        System.out.println("Starting to learn Software Desing Pattern.");
    }

    @Override
    public String getCourseName(){
        return"Programming";
    }

    @Override
    public String getTeacher(){
        return teacher;
    }
}
