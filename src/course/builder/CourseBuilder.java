package course.builder;

import course.Course;
import course.decorator.CertificateDecorator;
import course.decorator.GamificationDecorator;
import course.decorator.MentorSupportDecorator;
import course.factory.CourseFactory;
import model.Teacher;

public class CourseBuilder {
    private CourseBuilder(String type, Teacher teacher, boolean mentor, boolean certificate, boolean points){}

    public static class Builder {
        private String type;
        private Teacher teacher;
        private boolean haveMentor;
        private boolean haveCertificate;
        private boolean havePoints;

        public Builder setType(String type){
            this.type = type;
            return this;
        }

        public Builder setTeacher(Teacher teacher){
            this.teacher = teacher;
            return this;
        }

        public Builder setMentor(boolean mentor){
            this.haveMentor = mentor;
            return this;
        }

        public Builder setCertificate(boolean certificate){
            this.haveCertificate = certificate;
            return this;
        }

        public Builder setGamification(boolean points){
            this.havePoints = points;
            return this;
        }

        public Course build(){
            CourseFactory courseFactory = new CourseFactory();
            Course baseCourse = courseFactory.getCourse(type, teacher);
            if(baseCourse == null){
                return null;
            }
            if(haveMentor){
                baseCourse = new MentorSupportDecorator(baseCourse);
            }
            if(haveCertificate){
                baseCourse = new CertificateDecorator(baseCourse);
            }
            if(havePoints){
                baseCourse = new GamificationDecorator(baseCourse);
            }
            return baseCourse;
        }
    }
}
