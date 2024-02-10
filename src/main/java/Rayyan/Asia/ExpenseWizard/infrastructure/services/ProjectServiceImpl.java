package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.ProjectMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectService;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    @Override
    public Optional<ProjectDto> findById(String id) {
        var project = projectRepository.findById(id);
        return project.map(mapper::domainToDto);
    }

    @Override
    public ProjectDto save(ProjectDto project, String userId) {
        var projectModel = mapper.dtoToDomain(project);
        var user = new UserEntity();
        user.setId(userId);
        projectModel.setUser(user);
        projectRepository.save(projectModel);
        return mapper.domainToDto(projectModel);
    }

    @Override
    public Optional<ProjectDto> findProjectByNameAndUser(String name, String userId) {
        var project = projectRepository.findProjectByNameAndUser(name,userId);
        return project.map(mapper::domainToDto);
    }

    @Override
    public List<ProjectDto> findProjectsByUser(String id) {
       var projects = projectRepository.findProjectsByUserId(id);
       return mapper.domainToDto(projects);
    }
    @Override
    public boolean isProjectOwnedByUser(String projectId, String userId) {
        var project = projectRepository.findById(projectId);
        return project.map(value -> value.getUser().getId().equals(userId)).orElse(false);
    }
}
