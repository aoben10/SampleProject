package com.theobencode.victoroben.sampleproject

import com.theobencode.victoroben.sampleproject.network.RepoSearchResponse
import kotlinx.coroutines.Deferred

class GithubRepoRepository(private val githubService: GithubService) {

    fun searchRepositoriesByOrganizationAsync(organization: String): Deferred<RepoSearchResponse> {
        return githubService.searchRepositoriesAsync(organization)
    }
}