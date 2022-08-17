package team.kucing.anabulshopcare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.repository.UserAppRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserAppDetailsService implements UserDetailsService {

    @Autowired
    private UserAppRepository userAppRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp userApp = this.userAppRepository.findByEmail(email);
        return new UserAppDetailsImpl(userApp);
    }
}