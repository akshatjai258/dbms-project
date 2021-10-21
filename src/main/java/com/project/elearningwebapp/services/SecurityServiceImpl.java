package com.project.elearningwebapp.services;

import com.project.elearningwebapp.dao.userDAO;
import com.project.elearningwebapp.models.MyUserDetails;
import com.project.elearningwebapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class SecurityServiceImpl implements SecurityService {



    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private userDAO userdao;

    public SecurityServiceImpl(UserDetailServiceImpl userDetailsService, userDAO userdao) {

        this.userDetailsService = userDetailsService;
        this.userdao = userdao;
    }

    public SecurityServiceImpl() {
    }

    @Override
    public String findLoggedInUsername() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            return ((MyUserDetails) myUserDetails).getUser().getUsername();
        }
        return null;
    }

    @Override
    public int findLoggedInUserId() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (myUserDetails instanceof MyUserDetails) {
            return ((MyUserDetails) myUserDetails).getUser().getUser_id();
        }

        return 0;
    }

    @Override
    public String findLoggedInName() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            User user = ((MyUserDetails) myUserDetails).getUser();
            return user.getFirstName() + " " + user.getLastName();
        }
        return null;
    }

    @Override
    public String findLoggedInUserRole() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            String smallRole = ((MyUserDetails) myUserDetails).getUser().getRole();
            return smallRole;
        }
        return null;
    }

    @Override
    public User findLoggedInUser() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (myUserDetails instanceof MyUserDetails) {
            User user = ((MyUserDetails) myUserDetails).getUser();
            return user;
        }
        return null;
    }



    @Override
    public void autoLogout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public boolean isUserDeletedOrUpdated() {
        int userId = findLoggedInUserId();
        if (userId == 0)
            return false;
        User user = userdao.get(userId);
        if (user == null) {
            autoLogout();
            return true;
        } else {
            // Resetting the authentication object
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails oldUserDetails = (MyUserDetails)auth.getPrincipal();
            MyUserDetails newUserDetails = new MyUserDetails(user);
            boolean updated = !Objects.equals(user, oldUserDetails.getUser());
            if (updated) {
                Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, auth.getCredentials(),
                        newUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(newAuth);
                return true;
            }
        }
        return false;
    }
}

