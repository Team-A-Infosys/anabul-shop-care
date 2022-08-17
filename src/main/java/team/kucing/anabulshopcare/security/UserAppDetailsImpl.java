package team.kucing.anabulshopcare.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.kucing.anabulshopcare.entity.Role;
import team.kucing.anabulshopcare.entity.UserApp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAppDetailsImpl implements UserDetails {

    private UserApp userApp;

    public UserAppDetailsImpl(UserApp userApp){
        this.userApp = userApp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> roles = userApp.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role r : roles){
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return userApp.getPassword();
    }

    @Override
    public String getUsername() {
        return userApp.getEmail();
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
        return true;
    }

    public String getFirstame(){
        return this.userApp.getFirstName();
    }

    public String getLastname(){
        return this.userApp.getLastName();
    }
}