package dev.mju.jobport.modules.recruitment.application;

import dev.mju.jobport.modules.recruitment.domain.RecruitType;
import dev.mju.jobport.modules.recruitment.infrastructure.RecruitTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitTypeService {
    private final RecruitTypeRepository recruitTypeRepository;

    public List<RecruitType> getAvailableTypes() {
        return recruitTypeRepository.findAllOrdered();
    }
}
