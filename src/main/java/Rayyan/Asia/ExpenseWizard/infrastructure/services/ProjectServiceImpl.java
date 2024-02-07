package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.ProjectMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectService;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    @Override
    public Optional<Project> findById(String id) {
        return projectRepository.findById(id);
    }

    @Override
    public ProjectDto save(ProjectDto project) {
        var projectModel = mapper.dtoToDomain(project);
        projectRepository.save(projectModel);
        return mapper.domainToDto(projectModel);
    }

    @Override
    public Optional<ProjectDto> findProjectByNameAndUser(String name, String userId) {
        var project = projectRepository.findProjectByNameAndUser(name,userId);
        return project.map(mapper::domainToDto);
    }
}
