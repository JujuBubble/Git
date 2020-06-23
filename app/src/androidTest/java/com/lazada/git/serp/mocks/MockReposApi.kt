package com.lazada.git.serp.mocks

import com.apollographql.apollo.ApolloClient
import com.lazada.git.RepoListQuery
import com.lazada.git.serp.api.ReposApi
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MockReposApi(apolloClient: ApolloClient) : ReposApi(apolloClient) {

    private var pageCount = 0

    override fun getRepos(cursor: String?): Observable<List<RepoListQuery.Edge>> {
        val currentResults = when (pageCount) {
            0 -> simulateLoading(pageCount)
            3, 6, 9 -> simulateErrorRefresh()
            else -> simulateValidResults(pageCount)
        }
        pageCount++
        return currentResults
    }

    private fun simulateLoading(pageCount: Int): Observable<List<RepoListQuery.Edge>> {
        return Observable.timer(0, TimeUnit.SECONDS)
            .map { mockResults(pageCount) }
            .delay(1, TimeUnit.SECONDS)
    }

    private fun simulateValidResults(pageCount: Int): Observable<List<RepoListQuery.Edge>> {
        return Observable.just(mockResults(pageCount))
    }

    private fun simulateErrorRefresh(): Observable<List<RepoListQuery.Edge>> {
        return Observable.error<List<RepoListQuery.Edge>>(Exception("ERROR SIMULATE"))
    }

    private fun mockResults(pageCount: Int): List<RepoListQuery.Edge> {
        val list = mutableListOf<RepoListQuery.Edge>()
        for (i in 1..PAGE_SIZE) {
            list.add(
                RepoListQuery.Edge(
                    cursor = "CURSOR_${i}",
                    node = RepoListQuery.Node(
                        asRepository = RepoListQuery.AsRepository(
                            name = "REPO NAME #${i + (PAGE_SIZE * pageCount)}",
                            description = "REPO DESC #${i + (PAGE_SIZE * pageCount)}",
                            owner = RepoListQuery.Owner(avatarUrl = TEST_AVATAR)
                        )
                    )
                )
            )
        }
        return list
    }

    companion object {
        private const val TEST_AVATAR =
            "https://www.pngitem.com/pimgs/m/422-4225920_whatsapp-sticker-png-memes-transparent-png.png"
        private const val PAGE_SIZE = 30
    }
}