package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Role;
import com.simbirsoft.kanbanboard.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  public Optional<Role> getRoleById(Long id) {
    return roleRepository.findById(id);
  }

  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

  public void deleteRoleById(Long id) {
    roleRepository.deleteById(id);
  }
}
