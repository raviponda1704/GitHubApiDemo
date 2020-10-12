package model;

import com.google.gson.annotations.SerializedName;

public class GitHubCommit {

    @SerializedName("sha")
    private String message;

    @SerializedName("commit")
    private NestedCommit nestedCommit;

    @SerializedName("message")
    private String commit;

    public GitHubCommit(
            String commit,
            String message
    )
    {
        this.setCommit(commit);
        this.setMessage(message);
    }

    public NestedCommit getNestedCommit(){
        return nestedCommit;
    }

    public String getMessage() {
        return message;
    }

    public String getCommit() {
        return commit;
    }

    private void setCommit(String commit) {
        this.commit = commit;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}
