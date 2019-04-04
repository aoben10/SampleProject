package com.theobencode.victoroben.sampleproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theobencode.victoroben.sampleproject.network.Repo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GithubViewModel(private val repository: GithubRepoRepository) : ViewModel(), CoroutineScope {

    private val _repositories = MutableLiveData<Resource<List<Repo>>>()
    val repositories: LiveData<Resource<List<Repo>>> = _repositories

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val uiScope = CoroutineScope(coroutineContext)

    fun searchGithubRepos(organization: String) {
        coroutineContext.cancelChildren()
        _repositories.value = Resource(dataState = DataState.LOADING, data = _repositories.value?.data, message = null)

        uiScope.launch {
            try {
                delay(300) // throttle query
                val sortedRepoResponse =
                        repository
                                .searchRepositoriesByOrganizationAsync(organization)
                                .await()
                                .repos
                                .sortedByDescending { repo -> repo.stargazersCount }
                _repositories.value = Resource(dataState = DataState.SUCCESS, data = sortedRepoResponse, message = null)
            } catch (ex: Exception) {
                if (ex !is CancellationException) {
                    Log.e("TAG", "An error occurred while getting repositories", ex)
                    _repositories.value = Resource(dataState = DataState.ERROR, data = _repositories.value?.data, message = ex.message)
                }
            }

        }
    }
}

enum class DataState { LOADING, SUCCESS, ERROR }

data class Resource<out T> constructor(val dataState: DataState, val data: T? = null, val message: String? = null)
