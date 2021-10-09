package edu.miu.cs590.SA.Ecommerce.service;


import edu.miu.cs590.SA.Ecommerce.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${credential.username}")
    private String myUsername;
    @Value("${credential.password}")
    private String password;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(myUsername);
        user.setPassword(password);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetails(user);
    }

    private List<GrantedAuthority> buildUserAuthority() {
        return null;
    }
}
