package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Question;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDAOImplTest {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private QuizDAO quizDAO;

    @BeforeEach
    void setUp() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/e-learning");
        dataSource.setUsername("root");
        dataSource.setPassword("Akshat@123");

        questionDAO = new QuestionDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());
        quizDAO = new QuizDAOImpl(new JdbcTemplate(dataSource), new PreparedStatementUtil());

    }

    @Test
    void save() {
        Question question = new Question();
        question.setQuestionText("testing..");
        question.setOptionOne("hello1");
        question.setOptionTwo("hello2");
        question.setOptionThree("hello3");
        question.setOptionFour("hello4");
        question.setAnswer("hello1");
        question.setQuiz(quizDAO.get(2));
        questionDAO.save(question);

    }

    @Test
    void delete() {
        questionDAO.delete(2);
    }

    @Test
    void update() {
        Question question = questionDAO.get(3);
        System.out.println(question);
        question.setQuestionText("hello");
        questionDAO.update(question);

    }

    @Test
    void get() {

    }

    @Test
    void findByCourseId() {
        System.out.println(questionDAO.findByQuizId(2));
    }
}