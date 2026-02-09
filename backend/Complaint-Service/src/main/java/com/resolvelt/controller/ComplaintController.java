package com.resolvelt.controller;

import com.resolvelt.dto.ComplaintRequest;
import com.resolvelt.entity.Complaint;
import com.resolvelt.security.JwtUtil;
import com.resolvelt.service.ComplaintService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final JwtUtil jwtUtil;

    public ComplaintController(ComplaintService complaintService,
                               JwtUtil jwtUtil) {
        this.complaintService = complaintService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ CREATE COMPLAINT (JWT → UUID)
    @PostMapping
    public Complaint createComplaint(
            @RequestBody ComplaintRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token = authHeader.substring(7);

        // 🔥 JWT SUBJECT = USER UUID
        UUID userId = UUID.fromString(jwtUtil.getSubject(token));

        return complaintService.createComplaint(request, userId);
    }

    // ✅ USER: apni hi complaints
    @GetMapping("/user")
    public List<Complaint> getMyComplaints(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token = authHeader.substring(7);

        // 🔥 SAME RULE HERE
        UUID userId = UUID.fromString(jwtUtil.getSubject(token));

        return complaintService.getComplaintsByUser(userId);
    }

    // ✅ ADMIN / INTERNAL
    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }
}
