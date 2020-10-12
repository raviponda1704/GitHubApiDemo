package clienApi;

import java.util.List;

import model.GitHubCommit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GitHubCommitEndPoint {

    public interface GitCommitEndPoint {

        @GET("/repos/{user}/{repo}/commits")
        Call<List<GitHubCommit>> getCommits(@Path("user") String name, @Path("repo") String repos);
    }
}
