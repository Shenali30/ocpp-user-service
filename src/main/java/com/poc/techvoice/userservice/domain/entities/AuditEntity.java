package com.poc.techvoice.userservice.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class AuditEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedOn;
}
