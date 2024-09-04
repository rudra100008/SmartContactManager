package com.smartcontactmanager.Config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartcontactmanager.Entity.User;

public class CustomerUserDetails implements UserDetails {
    private User user;
    public CustomerUserDetails(User user)
    {
        super();
        this.user=user;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority=  new SimpleGrantedAuthority(user.getPosition());
        return List.of(simpleGrantedAuthority);//this list is immutable(cannot be changed or updated) and small in size
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
       return user.getUsername();
    }
    
}
