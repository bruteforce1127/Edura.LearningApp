package com.kucp1127.KidsLearningApp.Security_Configuration.SecurityConfiguration;

import com.kucp1127.KidsLearningApp.Security_Configuration.Model.Child;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class ChildPrincipal implements UserDetails {

    private Child child;

    public ChildPrincipal(Child child) {
        this.child = child;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You could assign a different role name, e.g. "ROLE_CHILD"
        return Collections.singleton(new SimpleGrantedAuthority("CHILD"));
    }

    @Override
    public String getPassword() {
        return child.getPassword();
    }

    @Override
    public String getUsername() {
        return child.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Adjust logic if you track expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Adjust logic if you track lock status
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Adjust logic if you track credential expiry
    }

    @Override
    public boolean isEnabled() {
        return true;  // Adjust logic if you track enabled/disabled
    }
}
