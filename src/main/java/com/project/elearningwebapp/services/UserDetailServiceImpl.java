package com.project.elearningwebapp.services;

import com.project.elearningwebapp.dao.userDAO;
import com.project.elearningwebapp.models.MyUserDetails;
import com.project.elearningwebapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private userDAO userDao;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username");
        }

        return new MyUserDetails(user);
    }



}
