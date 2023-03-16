package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Role;
import com.simbirsoft.kanbanboard.model.User;
import com.simbirsoft.kanbanboard.repository.RoleRepository;
import com.simbirsoft.kanbanboard.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  public UserService(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public User createUser(User user) {
    Role role = roleRepository.findById(user.getRole().getId())
        .orElseThrow(
            () -> new IllegalArgumentException("Invalid Role Id: " + user.getRole().getId()));
    user.setRole(role);
    return userRepository.save(user);
  }

  public User updateUser(Long id, User user) {
    User existingUser = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + id));
    existingUser.setUsername(user.getUsername());
    existingUser.setPassword(user.getPassword());
    existingUser.setEmail(user.getEmail());
    Role role = roleRepository.findById(user.getRole().getId())
        .orElseThrow(
            () -> new IllegalArgumentException("Invalid Role Id: " + user.getRole().getId()));
    existingUser.setRole(role);
    return userRepository.save(existingUser);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
