package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.models.Topic;

import java.util.List;

public interface TopicDAO {

    // save a topic
    public Topic save(Topic topic);

    // update a topic
    public void update(Topic topic);

    // delete a topic
    public void delete(int topicId);

    // listByCourseId
    public List<Topic> findByCourseID(int courseId);

    // get single topic
    public Topic get(int topicId);

    public int countLectures(int courseId);

}
