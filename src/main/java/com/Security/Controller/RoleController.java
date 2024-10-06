package com.Security.Controller;

import com.Security.DTO.RoleDTO;
import com.Security.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //Register new role
    @PostMapping("/register")
    public ResponseEntity<String> createRole(@RequestBody RoleDTO roleDTO){
        try{
            roleService.createRole(roleDTO);
            return ResponseEntity.ok("Role Created SuccesFully");

        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
