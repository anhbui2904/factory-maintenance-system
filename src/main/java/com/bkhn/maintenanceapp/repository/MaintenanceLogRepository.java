package com.bkhn.maintenanceapp.repository;

import com.bkhn.maintenanceapp.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {
    // Hiện tại chưa cần hàm tìm kiếm đặc biệt nào
}