package course.decorator;

import course.Course;

public class MentorSupportDecorator extends CourseDecorator{
    public MentorSupportDecorator(Course course) {
        super(course);
    }

    @Override
    public void deliverContent(){
        super.deliverContent();
        System.out.println("\nMentor guidance activated. Now if you have questions related to the course you can text to your mentor.");
    }
}
