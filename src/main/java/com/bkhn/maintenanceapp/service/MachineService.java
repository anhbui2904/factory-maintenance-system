package com.bkhn.maintenanceapp.service;

import com.bkhn.maintenanceapp.entity.Machine;
import com.bkhn.maintenanceapp.entity.MaintenanceLog;
import com.bkhn.maintenanceapp.repository.MachineRepository;
import com.bkhn.maintenanceapp.repository.MaintenanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private MaintenanceLogRepository maintenanceLogRepository;

    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    public Machine getMachineById(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid machine Id: " + id));
    }

    public void saveMachine(Machine machine) {
        machineRepository.save(machine);
    }

    @Transactional
    public void addMaintenanceLog(Long machineId, MaintenanceLog log) {
        Machine machine = getMachineById(machineId);
        log.setMachine(machine);

        if (log.getMaintenanceDate() == null) {
            log.setMaintenanceDate(LocalDate.now());
        }

        maintenanceLogRepository.save(log);

        LocalDate nextDate = log.getMaintenanceDate().plusMonths(machine.getMaintenanceIntervalMonths());

        machine.setLastMaintenanceDate(log.getMaintenanceDate());
        machine.setNextMaintenanceDate(nextDate);
        machine.setStatus("ACTIVE");

        machineRepository.save(machine);
    }
}