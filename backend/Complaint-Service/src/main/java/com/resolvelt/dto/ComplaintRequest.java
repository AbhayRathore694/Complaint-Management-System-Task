package com.resolvelt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ComplaintRequest {

    private String title;
    private String description;
}
