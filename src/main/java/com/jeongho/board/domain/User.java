package com.jeongho.board.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class User implements UserDetails {

    private Long no;

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    @Size(min = 8, max = 16, message = "아이디는 최소 8글자 최대 16글자까지 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어와 숫자로만 이루어져야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Size(min = 8, max = 30, message = "비밀번호는 최소 8글자 최대 30글자 까지 가능합니다.")
    // 정규 표현식 왤캐 어렵냐,,,
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z[0-9]@$!%*#?&]$", message = "비밀번호는 특수문자, 숫자, 영어로 구성되어야 합니다.")
    private String password;

    private String role;

    public User(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public User(Long no, String name, String id, String password) {
        this.no = no;
        this.name = name;
        this.id = id;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> this.role);
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
