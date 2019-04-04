package com.theobencode.victoroben.sampleproject.network

import com.squareup.moshi.Json

data class Repo(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "full_name") val fullName: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "stargazers_count") val stargazersCount: Int = 0,
    @field:Json(name = "html_url") val url: String
)