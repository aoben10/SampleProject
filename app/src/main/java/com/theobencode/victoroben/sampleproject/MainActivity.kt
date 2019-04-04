package com.theobencode.victoroben.sampleproject

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.theobencode.victoroben.sampleproject.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_main.emptyRepoView
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_main.repoRecyclerView
import kotlinx.android.synthetic.main.activity_main.searchEditText
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repoAdapter = RepoAdapter { repoUrl ->
            WebViewActivity.navigateToGithubWebView(this, repoUrl)
        }
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        repoRecyclerView.apply {
            this.layoutManager = layoutManager
            adapter = repoAdapter
            addItemDecoration(dividerItemDecoration)
        }

        getViewModel<GithubViewModel>().repositories.observe(this, Observer { resource ->
            when (resource.dataState) {
                DataState.LOADING -> progressBar.visibility = View.VISIBLE
                DataState.SUCCESS -> {
                    if (resource.data?.isEmpty() == true) {
                        emptyRepoView.visibility = View.VISIBLE
                        repoRecyclerView.visibility = View.GONE
                    } else {
                        emptyRepoView.visibility = View.GONE
                        repoRecyclerView.visibility = View.VISIBLE
                    }
                    progressBar.visibility = View.GONE
                    repoAdapter.submitList(resource.data)
                }
                DataState.ERROR   -> {
                    Toast.makeText(this, R.string.an_error_has_occured, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
            }
        })

        searchEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty() && currentFocus == searchEditText) {
                    getViewModel<GithubViewModel>().searchGithubRepos(s.toString())
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(bundleOf(SEARCH_TEXT_STATE_KEY to searchEditText.text.toString()))
    }

    companion object {
        const val SEARCH_TEXT_STATE_KEY = "Search_Query"
    }
}
