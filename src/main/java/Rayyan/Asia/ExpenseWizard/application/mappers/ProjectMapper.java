package Rayyan.Asia.ExpenseWizard.application.mappers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.project.ProjectDto;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {ExpenseMapper.class,UserMapper.class}, componentModel = "spring")
public interface ProjectMapper{
    ProjectDto domainToDto(Project project);
    Project dtoToDomain(ProjectDto dto);
    List<ProjectDto> domainToDto(List<Project> project);
    List<Project> dtoToDomain(List<ProjectDto> dto);
}
