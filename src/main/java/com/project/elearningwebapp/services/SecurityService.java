package com.project.elearningwebapp.services;

import com.project.elearningwebapp.models.User;
import org.springframework.stereotype.Service;


public interface SecurityService {
    public String findLoggedInUsername();

    public int findLoggedInUserId();

    public String findLoggedInName();

    public String findLoggedInUserRole();

    public User findLoggedInUser();


    public void autoLogout();

    public boolean isUserDeletedOrUpdated();
}
