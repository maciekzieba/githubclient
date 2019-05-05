package githubclient.domain.exception;

public class ProjectNotFound extends Exception {
    public ProjectNotFound(String message) {
        super(message);
    }
}