package com.mvp.challenge.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole implements java.io.Serializable, GrantedAuthority {

    private Role role;

    @Override
    public String getAuthority() {
        return role.getName();
    }
}

