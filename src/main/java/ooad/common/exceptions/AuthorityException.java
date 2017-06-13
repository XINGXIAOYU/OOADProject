package ooad.common.exceptions;

import ooad.common.Role;

/**
 * Created by xingxiaoyu on 17/6/12.
 */
public class AuthorityException extends Exception {
    private Role role;

    public AuthorityException(Role role) {
        this.role = role;
    }

    @Override
    public String getMessage() {
        return "Permission Denied. Present Role: " + this.role;
    }
}

