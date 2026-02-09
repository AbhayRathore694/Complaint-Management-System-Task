package com.resolvelt.controller;

import com.resolvelt.dto.DashboardResponse;
//import com.resolvelt.dto.StatusUpdateRequest;
import com.resolvelt.dto.UpdateStatusRequest;
import com.resolvelt.entity.Complaint;
import com.resolvelt.security.JwtUtil;
import com.resolvelt.service.AdminService;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    public AdminController(AdminService adminService, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ GET ALL COMPLAINTS (ADMIN ONLY)
    @GetMapping("/complaints")
    public List<Complaint> getAllComplaints(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token = authHeader.substring(7);

        String role = jwtUtil.getRole(token);

        // 🔥 NULL + ROLE SAFETY
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Access denied: ADMIN only"
            );
        }

        return adminService.getAllComplaints();
    }

    // ✅ UPDATE COMPLAINT STATUS
    @PutMapping("/complaints/{id}/status")
    public Complaint updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateStatusRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token = authHeader.substring(7);

        String role = jwtUtil.getRole(token);
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: ADMIN only");
        }

        return adminService.updateStatus(id, request.getStatus());
    }
}
