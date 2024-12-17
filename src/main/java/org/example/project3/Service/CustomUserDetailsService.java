package org.example.project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.project3.Entity.User;
import org.example.project3.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(email + "해당 이메일의 유저가 없습니다 "));

        // 특정 이메일에 대해 admin 권한 부여
        if ("knh3136@naver.com".equals(email)) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword()) // DB에서 가져온 암호화된 비밀번호
                    .authorities("ROLE_ADMIN") // 관리자 권한 부여
                    .build();
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_USER") // 기본 권한을 부여
                .build();
         //시큐리티 유저객체
    }



}
