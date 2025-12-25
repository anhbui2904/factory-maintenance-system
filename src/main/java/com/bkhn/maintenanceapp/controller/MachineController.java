package com.bkhn.maintenanceapp.controller;

import com.bkhn.maintenanceapp.entity.Machine;
import com.bkhn.maintenanceapp.entity.MaintenanceLog;
import com.bkhn.maintenanceapp.repository.MachineRepository;
import com.bkhn.maintenanceapp.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class MachineController {

    @Autowired
    private MachineService machineService;

    @Autowired
    private MachineRepository machineRepository; // Dùng để lấy số liệu thống kê cho Dashboard

    // --- 1. DASHBOARD (TRANG CHỦ - MỚI THÊM) ---
    @GetMapping("/")
    public String dashboard(Model model) {
        // Thống kê tổng số máy
        long totalMachines = machineRepository.count();

        // Thống kê số máy đang chạy (ACTIVE)
        long activeMachines = machineRepository.countByStatus("ACTIVE");

        // Tìm các máy cần bảo trì (Ngày bảo trì <= 7 ngày tới)
        LocalDate limitDate = LocalDate.now().plusDays(7);
        var warningMachines = machineRepository.findByNextMaintenanceDateBefore(limitDate);

        // Gửi số liệu sang giao diện dashboard
        model.addAttribute("totalMachines", totalMachines);
        model.addAttribute("activeMachines", activeMachines);
        model.addAttribute("maintenanceCount", warningMachines.size());
        model.addAttribute("warningMachines", warningMachines);

        return "dashboard"; // Trả về file dashboard.html
    }

    // --- 2. DANH SÁCH MÁY ---
    @GetMapping("/machines")
    public String listMachines(Model model) {
        model.addAttribute("listMachines", machineService.getAllMachines());
        return "machine-list";
    }

    // --- 3. THÊM MÁY MỚI ---
    @GetMapping("/machines/add")
    public String showAddMachineForm(Model model) {
        Machine machine = new Machine();
        machine.setInstallDate(LocalDate.now());
        machine.setMaintenanceIntervalMonths(6);
        model.addAttribute("machine", machine);
        return "machine-add";
    }

    @PostMapping("/machines/save")
    public String saveMachine(@ModelAttribute Machine machine) {
        machine.setStatus("ACTIVE");
        if (machine.getInstallDate() != null && machine.getMaintenanceIntervalMonths() != null) {
            LocalDate nextDate = machine.getInstallDate().plusMonths(machine.getMaintenanceIntervalMonths());
            machine.setNextMaintenanceDate(nextDate);
            machine.setLastMaintenanceDate(machine.getInstallDate());
        }
        machineService.saveMachine(machine);
        return "redirect:/machines";
    }

    // --- 4. CHI TIẾT MÁY ---
    @GetMapping("/machines/{id}")
    public String viewMachineDetail(@PathVariable Long id, Model model) {
        model.addAttribute("machine", machineService.getMachineById(id));
        return "machine-detail";
    }

    // --- 5. QUẢN LÝ LOG BẢO TRÌ ---
    @GetMapping("/machines/{id}/log/add")
    public String showAddLogForm(@PathVariable Long id, Model model) {
        MaintenanceLog log = new MaintenanceLog();
        model.addAttribute("machineId", id);
        model.addAttribute("maintenanceLog", log);
        return "maintenance-form";
    }

    @PostMapping("/machines/{id}/log/save")
    public String saveMaintenanceLog(@PathVariable Long id, @ModelAttribute MaintenanceLog maintenanceLog) {
        machineService.addMaintenanceLog(id, maintenanceLog);
        return "redirect:/machines/" + id;
    }
}