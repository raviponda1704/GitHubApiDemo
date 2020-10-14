package com.example.githubapidemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;
import android.widget.Toast;

import adapter.CommitAdapter;
import clienApi.ClientApi;
import clienApi.GitHubCommitEndPoint;
import model.GitHubCommit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.HORIZONTAL;

public class MainActivity extends AppCompatActivity {

    //Username and repo for this demo project
    private String Username = "raviponda1704";
    private String RepoDetails = "GithubApidemo";

    TextView commitHeaderTV ;
    RecyclerView mRecyclerView;
    List<GitHubCommit> myDataSource = new ArrayList<>();
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commitHeaderTV = findViewById(R.id.commit_details);
        commitHeaderTV.setText(Username + " - " + RepoDetails);

        mRecyclerView=  findViewById(R.id.commit_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new CommitAdapter(myDataSource, R.layout.list_commit_item,
                getApplicationContext());

        mRecyclerView.setAdapter(myAdapter);

        //checking for network connectivity
        if (!isNetworkAvailable()) {
            Toast toast = Toast.makeText(this,"Check network connection !!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            //Fetch Commit Data
            fetchCommitData();
        }
    }

    public void fetchCommitData(){

        GitHubCommitEndPoint apiService =
                ClientApi.getClient().create(GitHubCommitEndPoint.class);

        Call<List<GitHubCommit>> call = apiService.getCommits(Username,RepoDetails);

        call.enqueue(new Callback<List<GitHubCommit>>() {
            @Override
            public void onResponse(Call<List<GitHubCommit>> call, Response<List<GitHubCommit>> response) {
                if (response.isSuccessful()) {
                    myDataSource.clear();
                    myDataSource.addAll(response.body());
                    myAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Request failed. Something went wrong !!",
                            Toast.LENGTH_SHORT).show();
                    Log.e("Commit", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<GitHubCommit>> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(MainActivity.this,
                        "Request failed. Check your internet connection",
                        Toast.LENGTH_SHORT).show();
                Log.e("Commit", t.toString());
            }

        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
