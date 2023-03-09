package org.icddrb.otp_auth.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, CustomAuditingEntityListener.class})
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long id;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    @Column(name = "CREATE_DATE_TIME", updatable = false)
    private LocalDateTime createDateTime;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE_TIME")
    private LocalDateTime updateDateTime;

    private int projectId;
}
