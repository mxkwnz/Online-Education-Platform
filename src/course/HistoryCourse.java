package course;

public class HistoryCourse implements Course {
    private final String teacher;

    public HistoryCourse(String teacher){
        this.teacher = teacher;
    }

    @Override
    public void deliverContent(){
        System.out.println("Starting to learn History Of Kazakhstan.");
    }

    @Override
    public String getCourseName(){
        return "History of Kazakhstan";
    }

    @Override
    public String getTeacher(){
        return teacher;
    }
}
