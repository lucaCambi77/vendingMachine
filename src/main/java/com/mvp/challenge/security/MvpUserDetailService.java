/**
 *
 */
package com.mvp.challenge.security;

import com.mvp.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MvpUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        com.mvp.challenge.domain.user.User user = userRepository.get(userName);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        user.getUserRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
            role.getRole().getPrivileges().forEach(p -> {
                        grantedAuthorities.add(new SimpleGrantedAuthority(p.getPrivilege()));
                    }
            );
        });

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
