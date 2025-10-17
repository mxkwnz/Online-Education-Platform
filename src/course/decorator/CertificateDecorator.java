package course.decorator;

import course.Course;

public class CertificateDecorator extends CourseDecorator{
    public CertificateDecorator(Course course) {
        super(course);
    }

    @Override
    public void deliverContent(){
        super.deliverContent();
        System.out.println("You may learn the course, after finishing it you will get the certificate.");
    }
}
