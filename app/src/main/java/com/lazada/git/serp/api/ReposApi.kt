package com.lazada.git.serp.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers.NETWORK_FIRST
import com.apollographql.apollo.rx2.Rx2Apollo
import com.lazada.git.RepoListQuery
import io.reactivex.Observable

open class ReposApi constructor(private val apolloClient: ApolloClient) {
    open fun getRepos(cursor: String? = null): Observable<List<RepoListQuery.Edge>> {
        val query = RepoListQuery(
            query = REPO_QUERY_STRING,
            first = Input.optional(REPO_QUERY_PAGE_SIZE),
            cursor = Input.optional(cursor)
        )
        val networkQuery = apolloClient.query(query)
            .responseFetcher(NETWORK_FIRST)

        return Rx2Apollo.from(networkQuery).flatMap {
            it.errors?.let { errors ->
                ApolloException(errors[0].message).let { exception ->
                    return@flatMap Observable.error<List<RepoListQuery.Edge>>(exception)
                }
            } ?: run {
                it.data?.search?.edges?.let { list ->
                    return@flatMap Observable.just<List<RepoListQuery.Edge>>(list.filterNotNull())
                } ?: run {
                    return@flatMap Observable.just<List<RepoListQuery.Edge>>(listOf())
                }
            }
        }
    }

    companion object {
        const val REPO_QUERY_PAGE_SIZE = 20
        const val REPO_QUERY_STRING = "stars:>1600"
    }
}