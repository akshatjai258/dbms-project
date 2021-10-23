package com.project.elearningwebapp.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class PreparedStatementUtil {
    public void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }
}