package com.example.HealthCareAppointment.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HealthCareAppointment.Model.User;
import com.example.HealthCareAppointment.Request.LoginRequest;
import com.example.HealthCareAppointment.Response.ApiResponse;
import com.example.HealthCareAppointment.Service.User.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/user/getAll")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse("Found", userList));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        // trả về token cho user
        try {
            String token = userService.verify(loginRequest);
            return ResponseEntity.ok(new ApiResponse("Successfully created token", token));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(new ApiResponse("Login failed", loginRequest));
        }
    }


}
