package githubclient.domain;

import com.google.gson.annotations.SerializedName;

public class Project {

    @SerializedName("id")
    final String id;

    @SerializedName("stargazers_count")
    final int stars;
    
    public Project(String id, int stars) {
        this.id = id;
        this.stars = stars;
    }

    public int GetStars() {
        return this.stars;
    }

    public String GetId() {
        return this.id;
    }
}