package com.example.springproject.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void delete(Role role) {
        roleRepository.delete(role);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
