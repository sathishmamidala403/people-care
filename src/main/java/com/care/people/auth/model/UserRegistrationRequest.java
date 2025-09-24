package com.care.people.auth.model;

public class UserRegistrationRequest {
    private String username;
    private String password;
    private String role;
    private Long profileId;

    public UserRegistrationRequest(String username, String password, String role, Long profileId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
