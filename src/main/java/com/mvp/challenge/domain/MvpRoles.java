package com.mvp.challenge.domain;

public enum MvpRoles {

    SELLER(Code.SELLER),
    BUYER(Code.BUYER);

    private final String authority;

    MvpRoles(String authority) {
        this.authority = authority;
    }

    public final String getAuthority() {
        return this.authority;
    }

    public class Code {
        public static final String SELLER = "ROLE_SELLER";
        public static final String BUYER = "ROLE_BUYER";
    }
}
