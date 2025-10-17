package course.factory;

import course.*;
import model.Teacher;

public class CourseFactory {
    public Course getCourse(String courseType, Teacher teacher){
        if(courseType.equalsIgnoreCase("Mathematics")){
            return new MathCourse(teacher.getName());
        }
        if(courseType.equalsIgnoreCase("Programming")){
            return new ProgrammingCourse(teacher.getName());
        }
        if(courseType.equalsIgnoreCase("History")){
            return new HistoryCourse(teacher.getName());
        }
        if(courseType.equalsIgnoreCase("English")){
            return new EnglishCourse(teacher.getName());
        }
        return null;
    }
}
