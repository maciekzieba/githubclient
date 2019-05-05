package githubclient.domain;

import githubclient.domain.exception.Infrastructure;
import githubclient.domain.exception.ProjectNotFound;

public interface Repository {
    Project GetProject(String username, String projectName) throws Infrastructure, ProjectNotFound;
}