package com.project.elearningwebapp.dao;

import com.project.elearningwebapp.dao.rowmappers.CategoryRowMapper;
import com.project.elearningwebapp.models.Category;
import com.project.elearningwebapp.utils.PreparedStatementUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;



@Repository
public class CategoryDAOImpl implements CategoryDAO{

    public CategoryDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    @Override
    public Category save(Category category) {
        String sql = "INSERT INTO categories(Category_name, logo) VALUES(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"category_id"});
                preparedStatementUtil.setParameters(preparedStatement, category.getCategoryName(), category.getLogo());
                return preparedStatement;
            }
        }, keyHolder);

        int categoryId = keyHolder.getKey().intValue();
        category.setCategoryId(categoryId);
        return category;

    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM categories";
        List<Category> categories =jdbcTemplate.query(sql, new CategoryRowMapper());
        return categories;
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT * FROM categories WHERE Category_Id = ?";
        Category category = jdbcTemplate.queryForObject(sql, new Object[]{id}, new CategoryRowMapper());
        return category;
    }

    @Override
    public void update(Category category, int id) {
        String sql = "UPDATE categories SET Category_name=? where Category_Id = ?";
        jdbcTemplate.update(sql, category.getCategoryName(), id);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM categories WHERE Category_Id = ?";
        jdbcTemplate.update(sql, id);

    }
}
