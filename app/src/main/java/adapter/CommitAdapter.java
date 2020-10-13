package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.githubapidemo.R;

import java.util.List;

import model.GitHubCommit;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitsViewHolder>  {

    private List<GitHubCommit> commits;
    private int rowLayout;
    private Context context;


    public CommitAdapter(List<GitHubCommit> commits, int rowLayout, Context context) {
        this.setCommits(commits);
        this.setRowLayout(rowLayout);
        this.setContext(context);
    }
    public List<GitHubCommit> getCommits() {return commits;}

    private void setCommits(List<GitHubCommit> commits) {this.commits = commits;}

    public int getRowLayout() {return rowLayout;}

    private void setRowLayout(int rowLayout) {this.rowLayout = rowLayout;}

    public Context getContext() {return context;}

    private void setContext(Context context) {this.context = context;}

    public static class CommitsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout commitLayout;
        TextView userName;
        TextView shaCommit;
        TextView message;


        private CommitsViewHolder(View v) {
            super(v);
            commitLayout = v.findViewById(R.id.commit_item_layout);
            userName =  v.findViewById(R.id.userName);
            shaCommit =  v.findViewById(R.id.shaCommit);
            message =  v.findViewById(R.id.message);
        }
    }
    @Override
    public CommitAdapter.CommitsViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CommitAdapter.CommitsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CommitAdapter.CommitsViewHolder holder, final int position) {
        // holder.userName.setText("Ravi");
        holder.userName.setText("Name: " + commits.get(position).getNestedCommit().getAuthor().getName());
        holder.shaCommit.setText("Commit: " +commits.get(position).getCommit());
        holder.message.setText("Message: " + commits.get(position).getMessage());
    }

    @Override
    public int getItemCount() { return commits.size();}
}
