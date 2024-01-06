package Rayyan.Asia.ExpenseWizard.application.mappers;

import Rayyan.Asia.ExpenseWizard.application.dto.models.ProjectDto;
import Rayyan.Asia.ExpenseWizard.domain.models.Project;
import org.mapstruct.Mapper;

@Mapper(uses = ExpenseMapper.class, componentModel = "spring")
public interface ProjectMapper{
    ProjectDto domainToDto(Project project);
    Project dtoToDomain(ProjectDto dto);
}
