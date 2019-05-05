package githubclient.infrastructure;

public class JsonResponse {

    final int stars;

    public JsonResponse(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return this.stars;
    }
}