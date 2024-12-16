package org.example.project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.project3.DTO.UserDTO;
import org.example.project3.Entity.User;
import org.example.project3.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository uRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    public void save(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = mapToEntity(userDTO);
        user.setUserId(UUID.randomUUID().toString());
        uRepo.save(user);
    }

    private User mapToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginUserEmail = auth.getName();
        return uRepo.findByEmail( loginUserEmail ).orElseThrow(()->
                new UsernameNotFoundException("이메일을 찾을수 없습니다"));
    }
}
