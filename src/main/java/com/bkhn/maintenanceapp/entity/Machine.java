package com.bkhn.maintenanceapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate; // <--- Dòng này quan trọng, không được thiếu

@Entity
@Table(name = "machines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String code;
    private String model;
    private String location;

    @Column(name = "install_date")
    private LocalDate installDate; // Dùng thư viện java.time.LocalDate

    private String status;

    @Column(name = "last_maintenance_date")
    private LocalDate lastMaintenanceDate;

    @Column(name = "next_maintenance_date")
    private LocalDate nextMaintenanceDate;

    private Integer maintenanceIntervalMonths;

    // Thêm quan hệ 1-N (Một máy có nhiều log)
    // mappedBy = "machine" nghĩa là biến 'machine' bên bảng Log quản lý quan hệ này
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    private java.util.List<MaintenanceLog> maintenanceLogs;

}