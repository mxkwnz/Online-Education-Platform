package course.decorator;

import course.Course;

public abstract class CourseDecorator implements Course {
    protected Course course;

    public CourseDecorator(Course course) {
        this.course = course;
    }

    @Override
    public void deliverContent(){
        course.deliverContent();
    }

    @Override
    public String getCourseName(){
        return course.getCourseName();
    }

    @Override
    public String getTeacher(){
        return course.getTeacher();
    }
}
