package com.resolvelt.service;

import com.resolvelt.dto.ComplaintRequest;
import com.resolvelt.entity.Complaint;
import com.resolvelt.enums.ComplaintStatus;
import com.resolvelt.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    // ✅ CREATE COMPLAINT (JWT se aaya UUID use karo)
    public Complaint createComplaint(ComplaintRequest request, UUID userId) {

        Complaint complaint = new Complaint();
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());

        // 🔥 YAHI MAIN FIX
        complaint.setUserId(userId);

        complaint.setStatus(ComplaintStatus.OPEN);
        complaint.setCreatedAt(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    // ✅ USER ke complaints (UUID)
    public List<Complaint> getComplaintsByUser(UUID userId) {
        return complaintRepository.findByUserId(userId);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }
}
