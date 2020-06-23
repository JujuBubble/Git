package com.lazada.git.serp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lazada.git.R
import com.lazada.git.serp.paging.SerpPagedListAdapter
import com.lazada.git.serp.viewmodel.SerpViewModel
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_serp.*
import kotlinx.android.synthetic.main.screen_error.*
import javax.inject.Inject


class SerpActivity : AppCompatActivity() {
    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var serpViewModel: SerpViewModel

    private lateinit var adapter: SerpPagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        serpViewModel = ViewModelProviders.of(this, viewModelFactory).get(SerpViewModel::class.java)

        setContentView(R.layout.activity_serp)

        initRecyclerView()

        initObservers()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        adapter = SerpPagedListAdapter(picasso)
        recyclerView.adapter = adapter
    }

    private fun initObservers() {
        serpViewModel.repos().observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)
            swipeRefreshLayout.isRefreshing = false
        })
        serpViewModel.isDataLoaded().observe(this, Observer { dataLoaded(it) })
        serpViewModel.initialDataError().observe(this, Observer { initialDataError(it) })
        serpViewModel.moreDataError().observe(this, Observer { moreDataError(it) })

        retryButton.setOnClickListener { serpViewModel.refresh() }
        swipeRefreshLayout.setOnRefreshListener { serpViewModel.refresh() }
    }

    private fun dataLoaded(loaded: Boolean) {
        if (loaded) {
            screenLoading.animate()
                .setDuration(ANIMATION_DURATION)
                .alpha(0.1f)
                .withEndAction { screenLoading.isGone = loaded }
                .start()
        } else {
            screenLoading.alpha = 1.0f
            screenLoading.isGone = false
        }
    }

    private fun initialDataError(error: Error?) {
        screenError.isGone = error == null
    }

    private fun moreDataError(error: Error?) {
        if (error != null) {
            Snackbar.make(
                contentView,
                this.resources.getString(R.string.error_page_message),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(this.resources.getString(R.string.error_page_button)) {
                serpViewModel.retryNextPage()
            }.show()
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 400L
    }
}