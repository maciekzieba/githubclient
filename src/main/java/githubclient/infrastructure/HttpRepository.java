package githubclient.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import githubclient.domain.Project;
import githubclient.domain.Repository;
import githubclient.domain.exception.ProjectNotFound;
import githubclient.domain.exception.Infrastructure;;

@Component
class HttpRepository implements Repository {

    @Value("${infrastructure.apiBaseUrl}")
    private String apiBaseUrl;

    @Override
    public Project GetProject(String username, String projectName) throws Infrastructure, ProjectNotFound {
        URL url;
        try {
            url = new URL(String.format("https://%s/%s/%s", this.apiBaseUrl, username, projectName));
        } catch (MalformedURLException e) {
            throw new Infrastructure("Unable to build api url");
        }
        HttpsURLConnection con;
        try {
            con = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new Infrastructure(String.format("Unable to open connection %s", e.getMessage()));
        }
        try {
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-length", "0");
            con.setUseCaches(false);
            con.setAllowUserInteraction(false);
            con.connect();
        } catch (Exception e) {
            throw new Infrastructure(String.format("Unable to connect %s", e.getMessage()));
        }
        try {
            int status = con.getResponseCode();
            if (status != HttpsURLConnection.HTTP_OK) {
                throw new ProjectNotFound(String.format("Project %s not found.", url.toString()));
            }
        } catch (IOException e) {
            throw new Infrastructure(String.format("Unable to read response %s", e.getMessage()));
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            throw new Infrastructure(String.format("Unable to read response %s", e.getMessage()));
        }
        String inputLine;
        StringBuffer content = new StringBuffer();
        try {
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        } catch (IOException e) {
            throw new Infrastructure(String.format("Unable to read response %s", e.getMessage()));
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new Infrastructure(String.format("Unable to close connection %s", e.getMessage()));
        }
        Gson g = new Gson(); 
        try  {
            Project project = g.fromJson(content.toString(), Project.class);
            return project;
        } catch (JsonSyntaxException e) {
            throw new Infrastructure(String.format("Unable parse json %s", e.getMessage()));
        }
    }

}