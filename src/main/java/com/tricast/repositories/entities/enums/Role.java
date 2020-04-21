package com.tricast.repositories.entities.enums;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ADMIN(1), EMPLOYEE(2), GUEST(3);

    private int id;

    private static final Map<Integer, Role> BY_ID = new HashMap<>();

    static {
        for (Role role: values()) {
            BY_ID.put(role.id, role);
        }
    }

    private Role(int id) {
        this.id = id;
    }

    public static Role getById(int roleId) {
        return BY_ID.get(roleId);
    }
}
