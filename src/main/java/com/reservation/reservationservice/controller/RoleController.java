package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.dto.RoleDto;
import com.reservation.reservationservice.model.Role;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.payload.RolePayload;
import com.reservation.reservationservice.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/public/")
@CrossOrigin
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PostMapping("roles")
    public ResponseEntity<ResponseMessage> createRole(@RequestBody RolePayload rolePayload) {
        roleService.createRole(rolePayload);
        message = "Role created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PatchMapping("roles/{roleId}")
    public ResponseEntity<ResponseMessage> editRole(@PathVariable("roleId") String roleId,
                                                    @RequestBody RolePayload rolePayload) {
        roleService.editRole(roleId, rolePayload);
        message = "Role edited successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("roles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleDto> roleDTOS = roles.stream()
                .map(role -> this.modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(roleDTOS, HttpStatus.OK);
    }

    @GetMapping("roles/{roleId}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("roleId") String roleId) {
        Role role = roleService.getRole(roleId);
        RoleDto roleDto = this.modelMapper.map(role, RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @DeleteMapping("roles/{roleId}")
    public ResponseEntity<ResponseMessage> deleteRole(@PathVariable("roleId") String roleId) {
        roleService.deleteRole(roleId);
        message = "Role deleted successfully";
        return new ResponseEntity<>(new ResponseMessage(message) , HttpStatus.OK);
    }

}
