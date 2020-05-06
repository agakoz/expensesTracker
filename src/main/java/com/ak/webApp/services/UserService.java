package com.ak.webApp.services;

import com.ak.webApp.models.User;
import com.ak.webApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void createAndAddUser(User user) {

        String encodePassword = getEncodedPassword(user);
        user.setPassword(encodePassword);

        Integer id = getIdForNewUser(userRepository);
        user.setUserId(id);


         user.setRole("ROLE_USER");


        userRepository.save(user);
    }

    private String getEncodedPassword(User user) {
        String password = user.getPassword();
        return passwordEncoder.encode(password);
    }

    private Integer getIdForNewUser(UserRepository userRepository) {
        User lastUser = userRepository.findFirstByOrderByUserIdDesc();
        if (lastUser == null) {
            return 1;
        }
        Integer lastId = lastUser.getUserId();
        return ++lastId;
    }
}
