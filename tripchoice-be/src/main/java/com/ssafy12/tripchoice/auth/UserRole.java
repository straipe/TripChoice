package com.ssafy12.tripchoice.auth;

public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    String role;

    UserRole(String role) {
        this.role = role;
    }

    public String role(){
        return role;
    }

    // Enum 값을 기반으로 Enum 인스턴스를 찾는 메서드
    public static UserRole roleOf(String roleString) { //todo rename
        for (UserRole userRole : UserRole.values()) {
            if (userRole.role().equals(roleString)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No enum constant for roleString: " + roleString);
    }
}
