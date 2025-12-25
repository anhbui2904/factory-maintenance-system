package com.bkhn.maintenanceapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quan hệ N-1: Nhiều log thuộc về 1 máy
    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @Column(name = "maintenance_date")
    private LocalDate maintenanceDate; // Ngày thực hiện bảo trì

    private String technician; // Tên kỹ thuật viên (người sửa)

    @Column(length = 500)
    private String description; // Nội dung công việc (Sửa motor, thay dầu...)

    private String partsReplaced; // Linh kiện thay thế (nếu có)

    private Double cost; // Chi phí (Yên Nhật)
}