package com.mvp.challenge.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements java.io.Serializable {
    private String name;
    private List<Privilege> privileges;
}
