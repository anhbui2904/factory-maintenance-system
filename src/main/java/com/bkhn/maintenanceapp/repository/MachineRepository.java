package com.bkhn.maintenanceapp.repository;

import com.bkhn.maintenanceapp.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    // 1. Tìm máy có ngày bảo trì <= ngày nào đó (Ví dụ: <= Hôm nay là quá hạn)
    List<Machine> findByNextMaintenanceDateBefore(LocalDate date);

    // 2. Đếm số máy theo trạng thái (Ví dụ: Đếm xem bao nhiêu máy ACTIVE)
    long countByStatus(String status);
}