package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.Role;
import com.reservation.reservationservice.payload.RolePayload;
import com.reservation.reservationservice.repository.RoleRepository;
import com.reservation.reservationservice.service.RoleService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Vifieh Ruth
 * on 5/27/22
 */

@Service
public class RoleServiceImpl implements RoleService {
    private final Util util = new Util();

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void createRole(RolePayload rolePayload) {
        Role role = new Role();
        Optional<Role> role1 = roleRepository.findByRole(rolePayload.getRole());
        if(role1.isPresent()) {
            throw new ResourceAlreadyExistException("Role already exist");
        }
        role.setId(util.generateId());
        role.setRole(rolePayload.getRole());
        roleRepository.save(role);
    }

    @Override
    public void editRole(String roleId, RolePayload rolePayload) {
        Role role1 = getRole(roleId);
        role1.setRole(rolePayload.getRole());
        roleRepository.save(role1);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRole(String roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.orElseThrow(() -> new ResourceNotFoundException("Role not found with id - " + roleId));
        return role.get();
    }

    @Override
    public Role getRoleByName(ERole roleName) {
        Optional<Role> role = roleRepository.findByRole(roleName);
        role.orElseThrow(() -> new ResourceNotFoundException("Role not found with id - " + roleName));
        return role.get();
    }

    @Override
    public void deleteRole(String roleId) {
        roleRepository.deleteById(roleId);
    }
}
