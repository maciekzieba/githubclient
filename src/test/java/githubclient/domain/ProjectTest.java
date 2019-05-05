package githubclient.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class ProjectTest {
    
    @Test
    @DisplayName("sdads")
    public void testConstructor() {
        final Project project = new Project("some_id", 5);
        assertEquals("some_id", project.GetId());
        assertEquals(5, project.GetStars());        
    }
}