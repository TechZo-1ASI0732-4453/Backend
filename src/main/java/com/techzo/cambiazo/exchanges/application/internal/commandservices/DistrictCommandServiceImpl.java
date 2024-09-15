package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateDistrictCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.District;
import com.techzo.cambiazo.exchanges.domain.services.IDistrictCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IDepartmentRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IDistrictRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistrictCommandServiceImpl implements IDistrictCommandService {

    private final IDistrictRepository districtRepository;

    private final IDepartmentRepository departmentRepository;

    public DistrictCommandServiceImpl(IDistrictRepository districtRepository, IDepartmentRepository departmentRepository) {
        this.districtRepository = districtRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Optional<District>handle(CreateDistrictCommand command) {
        var department = departmentRepository.findById(command.departmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        if (districtRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("District with same name already exists");
        }
        var district = new District(command, department);
        var createdDistrict = districtRepository.save(district);
        return Optional.of(createdDistrict);
    }
}
