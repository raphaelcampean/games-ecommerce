package com.gamesecommerce.store.controller.admin;

import com.gamesecommerce.store.record.UserDTO;
import com.gamesecommerce.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/usuarios")
public class UserAdminController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> listAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {

        Page<UserDTO> users = userService.getUsers(pageable);

        return ResponseEntity.ok(users);
    }
}
