package com.example.githubapidemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import adapter.CommitAdapter;
import clienApi.ClientApi;
import clienApi.GitHubCommitEndPoint;
import model.GitHubCommit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String Username = "raviponda1704";
    private String RepoDetails = "GithubApidemo";

    RecyclerView mRecyclerView;
    List<GitHubCommit> myDataSource = new ArrayList<>();
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=  findViewById(R.id.commit_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new CommitAdapter(myDataSource, R.layout.list_commit_item,
                getApplicationContext());

        mRecyclerView.setAdapter(myAdapter);

        //Fetch Commit Data
        fetchCommitData();
    }

    public void fetchCommitData(){

        GitHubCommitEndPoint apiService =
                ClientApi.getClient().create(GitHubCommitEndPoint.class);

        Call<List<GitHubCommit>> call = apiService.getCommits(Username,RepoDetails);

        call.enqueue(new Callback<List<GitHubCommit>>() {
            @Override
            public void onResponse(Call<List<GitHubCommit>> call, Response<List<GitHubCommit>> response) {

                Log.d("Commits2",call.request().url().toString());
                Log.d("Commits2", new Gson().toJson(response.body()));
                myDataSource.clear();
                myDataSource.addAll(response.body());
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GitHubCommit>> call, Throwable t) {
                // Log error here since request failed
                Log.e("Commit", t.toString());
            }

        });
    }
}
