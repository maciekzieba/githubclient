package githubclient.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import githubclient.domain.Project;
import githubclient.domain.Repository;
import githubclient.domain.exception.Infrastructure;
import githubclient.domain.exception.ProjectNotFound;

@Component
public class ProjectStarsUseCase {

    @Autowired
    private Repository repository;

    public ProjectStarsUseCase(Repository repository) {
        this.repository = repository;
    }

    public int GetProjectStars(String username, String projectName) throws Infrastructure, ProjectNotFound {
        Project project;
        try {
            project = repository.GetProject(username, projectName);
        } catch (Exception e) {
           throw e;
        }
        return project.GetStars();
    }
}