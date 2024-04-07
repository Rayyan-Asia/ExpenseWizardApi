package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectListDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.ProjectMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.ProjectService;
import Rayyan.Asia.ExpenseWizard.domain.models.Expense;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    @Override
    @Transactional
    public Optional<ProjectDto> findById(String id) {
        var project = projectRepository.findById(id);
        return project.map(mapper::domainToDto);
    }

    @Override
    @Transactional
    public ProjectDto save(ProjectDto project, String userId) {
        var projectModel = mapper.dtoToDomain(project);
        var user = new UserEntity();
        user.setId(userId);
        projectModel.setUser(user);
        projectRepository.save(projectModel);
        return mapper.domainToDto(projectModel);
    }

    @Override
    @Transactional
    public void delete(String projectId) {
        var project = projectRepository.findById(projectId);
        project.ifPresent(projectRepository::delete);
    }

    @Override
    @Transactional
    public Optional<ProjectDto> findProjectByNameAndUser(String name, String userId) {
        var project = projectRepository.findProjectByNameAndUser(name,userId);
        return project.map(mapper::domainToDto);
    }

    @Override
    @Transactional
    public Page<ProjectDto> findProjectsByUser(String id, Pageable pageable) {
       return projectRepository.findProjectsByUserId(id, pageable).map(mapper::domainToDto);
    }
    @Override
    @Transactional
    public boolean isProjectOwnedByUser(String projectId, String userId) {
        var project = projectRepository.findById(projectId);
        if (project.isEmpty())
            throw new NotFoundException("Project Not Found");
        return project.map(value -> value.getUser().getId().equals(userId)).orElse(false);
    }

    @Override
    public Page<ProjectListDto> getProjectsWithCurrentMonthExpenses(String userId, Pageable pageable) {
        var list = new ArrayList<ProjectListDto>();
        var projects = projectRepository.findProjectsWithCurrentMonthExpenses(userId, pageable);
        for (Project project : projects.getContent()){
            double totalExpensesThisMonth = project.getExpenses().stream()
                    .mapToDouble(Expense::getCost)
                    .sum();
            var projectList = mapper.domainToListDto(project);
            projectList.setExpensesThisMonth((float) totalExpensesThisMonth);
            list.add(projectList);
        }

        return new PageImpl<>(list,pageable,projects.getTotalElements());
    }
}
