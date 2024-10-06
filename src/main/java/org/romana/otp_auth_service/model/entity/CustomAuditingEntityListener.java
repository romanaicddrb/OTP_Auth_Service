package org.romana.otp_auth_service.model.entity;


import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class CustomAuditingEntityListener {
    @PrePersist
    public void onPrePersist(BaseEntity baseEntity) {
        if (baseEntity.getCreateDateTime() == null) {
            baseEntity.setCreateDateTime(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void onPreUpdate(BaseEntity baseEntity) {
        if (baseEntity.getUpdateDateTime() == null) {
            baseEntity.setUpdateDateTime(LocalDateTime.now());
        }
    }
}
