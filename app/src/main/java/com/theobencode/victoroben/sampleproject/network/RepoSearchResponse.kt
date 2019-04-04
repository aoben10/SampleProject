package com.theobencode.victoroben.sampleproject.network

import com.squareup.moshi.Json

data class RepoSearchResponse(
    @field:Json(name = "incomplete_results") val incompleteResults: Boolean = false,
    @field:Json(name = "items") val repos: List<Repo>,
    @field:Json(name = "total_count") val totalCount: Int = 0
)