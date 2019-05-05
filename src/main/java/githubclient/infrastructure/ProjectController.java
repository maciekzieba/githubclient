package githubclient.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import githubclient.app.ProjectStarsUseCase;
import githubclient.domain.exception.Infrastructure;
import githubclient.domain.exception.ProjectNotFound;


@RestController
@RequestMapping("/project")
class ProjectController {

    @Autowired
    private ProjectStarsUseCase projectStarsUseCase;

    @GetMapping(path = "/{username}/{projectname}")
    public int index(@PathVariable String username, @PathVariable String projectname) {
        try {
            return this.projectStarsUseCase.GetProjectStars(username, projectname);
        } catch (ProjectNotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); 
        } catch (Infrastructure e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage()); 
        } 
    }
}