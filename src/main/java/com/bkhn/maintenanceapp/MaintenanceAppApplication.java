package com.bkhn.maintenanceapp;

import com.bkhn.maintenanceapp.entity.Machine;
import com.bkhn.maintenanceapp.entity.MaintenanceLog;
import com.bkhn.maintenanceapp.repository.MachineRepository;
import com.bkhn.maintenanceapp.repository.MaintenanceLogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class MaintenanceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaintenanceAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(MachineRepository machineRepo, MaintenanceLogRepository logRepo) {
        return args -> {
            // Kiểm tra nếu Database chưa có dữ liệu thì mới tạo
            if (machineRepo.count() == 0) {

                // --- 1. TẠO MÁY MÓC (MACHINES) ---

                // Máy 1: Máy cắt Laser (CNC)
                Machine m1 = new Machine(
                        null,
                        "CNCレーザー切断機", // Tên máy
                        "MC-01",
                        "Model X-2023",
                        "第1工場 - エリアA",
                        LocalDate.now().minusYears(1),
                        "ACTIVE",
                        LocalDate.now().minusMonths(1),
                        LocalDate.now().plusMonths(5),
                        6,
                        null // null ở đây là danh sách Log (vì mới tạo nên chưa có log)
                );

                // Máy 2: Máy ép nhựa (Injection Molding)
                Machine m2 = new Machine(
                        null,
                        "油圧式射出成形機", // Tên máy
                        "ME-05",
                        "Model Titan",
                        "第2工場 - エリアB",
                        LocalDate.now().minusYears(3),
                        "MAINTENANCE",
                        LocalDate.now().minusMonths(6),
                        LocalDate.now().minusDays(2),
                        3,
                        null // null danh sách Log
                );

                // Lưu Máy vào Database trước (để có ID)
                machineRepo.save(m1);
                machineRepo.save(m2);

                // --- 2. TẠO NHẬT KÝ BẢO TRÌ (LOGS) ---

                // Log 1: Bảo trì định kỳ cho máy Laser (m1)
                MaintenanceLog log1 = new MaintenanceLog(
                        null,
                        m1, // Gắn vào máy m1
                        LocalDate.now().minusMonths(1), // Ngày làm: 1 tháng trước
                        "田中 健太", // Kỹ thuật viên Tanaka
                        "定期点検：オイル交換、フィルター清掃", // Nội dung: Thay dầu, vệ sinh lọc
                        "オイルフィルター (Oil Filter)", // Linh kiện thay
                        5000.0 // Chi phí
                );

                // Log 2: Sửa chữa gấp cho máy Ép nhựa (m2)
                MaintenanceLog log2 = new MaintenanceLog(
                        null,
                        m2, // Gắn vào máy m2
                        LocalDate.now().minusMonths(6),
                        "佐藤 浩", // Kỹ thuật viên Sato
                        "緊急修理：油圧ポンプ異音確認", // Nội dung: Kiểm tra tiếng ồn bơm thủy lực
                        "パッキン交換 (O-Ring)", // Linh kiện thay
                        12000.0 // Chi phí
                );

                // Lưu Log vào Database
                logRepo.save(log1);
                logRepo.save(log2);

                System.out.println(">>> FULL JAPANESE DATA & LOGS CREATED SUCCESSFULLY <<<");
            }
        };
    }
}