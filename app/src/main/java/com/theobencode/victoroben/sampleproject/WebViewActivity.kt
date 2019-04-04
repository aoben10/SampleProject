package com.theobencode.victoroben.sampleproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.repoWebView

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.getStringExtra(EXTRA_WEB_VIEW_URL)
        repoWebView.loadUrl(url)
    }

    companion object {
        private const val EXTRA_WEB_VIEW_URL = "web_view_url"

        fun navigateToGithubWebView(activity: Activity, url: String) {
            val intent = Intent(activity, WebViewActivity::class.java).apply {
                putExtra(EXTRA_WEB_VIEW_URL, url)
            }
            activity.startActivity(intent)
        }

    }
}
