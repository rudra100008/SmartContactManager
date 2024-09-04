package com.smartcontactmanager.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Services.UserServices;

public class UserDetailsServiceImpl  implements UserDetailsService{

    @Autowired
    private UserServices userServices;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
        User user=  userServices.findUserByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException("Could not found user");
        }
        CustomerUserDetails customerUserDetails=new CustomerUserDetails(user);
        return customerUserDetails;
    }
    
}
