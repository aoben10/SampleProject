package com.theobencode.victoroben.sampleproject

import com.theobencode.victoroben.sampleproject.network.RepoSearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("/search/repositories")
    fun searchRepositoriesAsync(
            @Query("q") organization: String,
            @Query("sort") sort: String = "stars",
            @Query("sort") order: String = "desc",
            @Query("per_page") perPage: String = "3"
    ): Deferred<RepoSearchResponse>
}