package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.dao.rowmappers.CourseRowMapper;
import com.project.elearningwebapp.dao.rowmappers.EnrollmentRowMapper;
import com.project.elearningwebapp.models.Course;
import com.project.elearningwebapp.models.Enrollment;
import com.project.elearningwebapp.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EnrollmentDAOImpl implements EnrollmentDAO{
    public EnrollmentDAOImpl() {
    }

    public EnrollmentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentDAO studentDAO;


    @Override
    public void enroll(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments(student_id, course_id, enrollment_time) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, enrollment.getStudent().getStudentId(), enrollment.getCourse().getCourseId(), enrollment.getEnrollment_time());

    }

    @Override
    public void unEnroll(int studentId, int courseId) {
        String sql = "DELETE FROM enrollments WHERE student_id = ? AND course_id=?";
        jdbcTemplate.update(sql, studentId, courseId);

    }

    @Override
    public Boolean isEnrolled(int studentId, int courseId) {
        String sql = "SELECT count(*) FROM enrollments WHERE student_id = ? AND course_id=?";
        int x = jdbcTemplate.queryForObject(sql, new Object[]{studentId, courseId}, Integer.class);
        if(x>0){
            return true;
        }
        return  false;
    }

    @Override
    public int getTotalStudentEnrolled(int courseId) {
        String sql = "SELECT count(*) FROM enrollments WHERE course_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{courseId}, Integer.class);
    }

    @Override
    public List<Student> studentsEnrolled(int courseId) {
        // little difficult task because we get only the list of student ids

        String sql = "SELECT * FROM enrollments NATURAL JOIN students  NATURAL JOIN users NATURAL JOIN courses NATURAL JOIN categories WHERE course_id=?";
        List<Enrollment>lst = jdbcTemplate.query(sql, new Object[]{courseId}, new EnrollmentRowMapper());
        List<Student>ans = new ArrayList<Student>(lst.size());
        for (Enrollment e: lst) {
            ans.add(e.getStudent());
        }
        return ans;
    }

    @Override
    public Page<Course> coursesEnrolled(int studentId, Pageable pageable) {
        String sql = "SELECT * FROM enrollments NATURAL JOIN teachers  NATURAL JOIN users NATURAL JOIN courses NATURAL JOIN categories WHERE student_id=?";
        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("course_id");
        sql += " ORDER BY " + order.getProperty() + " "
                + order.getDirection().name()
                + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();



        List<Enrollment>lst = jdbcTemplate.query(sql, new Object[]{studentId}, new EnrollmentRowMapper());
        List<Course>courses = new ArrayList<Course>(lst.size());
        for (Enrollment e: lst) {
            courses.add(e.getCourse());
        }

        return new PageImpl<Course>(courses, pageable, count_new(studentId));
    }

    @Override
    public Integer getNoOfStudentsEnrolled(int courseId) {

        String sql = "SELECT count(*) FROM enrollments WHERE course_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{courseId}, Integer.class);

    }

    @Override
    public Integer count_new(int studentId) {
        String sql = "SELECT COUNT(*) FROM enrollments where student_id =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{studentId}, Integer.class);
    }


}
