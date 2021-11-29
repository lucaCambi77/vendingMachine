/**
 * 
 */
package com.mvp.challenge.security;

public interface SecurityService
{
    String findLoggedInUsername();

    boolean login(String username, String password);
}
