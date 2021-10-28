package com.project.elearningwebapp.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (!user.getEnabled()) {
            return grantList;
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole());
        grantList.add(authority);
        return grantList;
    }

    public void setFirstName(String fname){
        this.user.setFirstName(fname);
    }

    public void setLastName(String lname){
        this.user.setLastName(lname);
    }

    public void setEmailId(String email){
        this.user.setEmailId(email);
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    public MyUserDetails(User user) {
        this.user = user;
    }

    public MyUserDetails() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
