package com.lazada.git.config

import com.lazada.git.BuildConfig

class GitConfiguration {
    val gitGraphQLEndpoint: String = BuildConfig.GIT_ENDPOINT
    val gitToken: String = BuildConfig.GIT_TOKEN
}