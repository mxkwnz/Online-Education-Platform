package course.decorator;

import course.Course;

public class GamificationDecorator extends CourseDecorator{
    private boolean gamified = true;

    public GamificationDecorator(Course course) {
        super(course);
    }

    @Override
    public void deliverContent(){
        super.deliverContent();
        System.out.println("Gamification activated. Now after every lesson you will earn some points.");
    }

    public boolean isGamified() {
        return gamified;
    }
}
