package dev.mju.jobport.modules.recruitment.application;

import dev.mju.jobport.modules.recruitment.domain.EducationLevel;
import dev.mju.jobport.modules.recruitment.domain.EmploymentType;
import dev.mju.jobport.modules.recruitment.domain.Ncs;
import dev.mju.jobport.modules.recruitment.domain.WorkField;
import dev.mju.jobport.modules.recruitment.domain.WorkLocation;
import dev.mju.jobport.modules.recruitment.infrastructure.EducationLevelRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.EmploymentTypeRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.NcsRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.WorkFieldRepository;
import dev.mju.jobport.modules.recruitment.infrastructure.WorkLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentFilterOptionService {

    private final WorkLocationRepository workLocationRepository;
    private final EmploymentTypeRepository employmentTypeRepository;
    private final EducationLevelRepository educationLevelRepository;
    private final WorkFieldRepository workFieldRepository;
    private final NcsRepository ncsRepository;

    public List<WorkLocation> getWorkLocations() {
        return workLocationRepository.findAllOrdered();
    }

    public List<EmploymentType> getEmploymentTypes() {
        return employmentTypeRepository.findAllOrdered();
    }

    public List<EducationLevel> getEducationLevels() {
        return educationLevelRepository.findAllOrdered();
    }

    public List<WorkField> getWorkFields() {
        return workFieldRepository.findAllOrdered();
    }

    public List<Ncs> getNcsList() {
        return ncsRepository.findAllOrdered();
    }
}
