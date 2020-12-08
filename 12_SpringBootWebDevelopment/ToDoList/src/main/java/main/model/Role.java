package main.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {   //что-то вроде заглушки. Нам пока не надо разграничивать доступ
    USER,
    MODERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
