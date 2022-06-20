package com.reservation.reservationservice.service;

import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.model.Role;
import com.reservation.reservationservice.payload.RolePayload;

import java.util.List;

public interface RoleService {

    void createRole(RolePayload rolePayload);

    void editRole(String roleId, RolePayload rolePayload);

    List<Role> getAllRoles();

    Role getRole(String roleId);

    Role getRoleByName(ERole roleName);

    void deleteRole(String roleId);

}
