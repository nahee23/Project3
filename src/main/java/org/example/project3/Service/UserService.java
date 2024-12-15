package org.example.project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.project3.DTO.UserDTO;
import org.example.project3.Entity.User;
import org.example.project3.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository uRepo;
    private final ModelMapper modelMapper;

    public void save(UserDTO userDTO) {
        User user = mapToEntity(userDTO);
        user.setUserId(UUID.randomUUID().toString());
        uRepo.save(user);
    }

    private User mapToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
