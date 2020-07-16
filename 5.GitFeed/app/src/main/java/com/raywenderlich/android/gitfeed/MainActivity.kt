package com.raywenderlich.android.gitfeed

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  private lateinit var viewModel: MainViewModel

  private val adapter = EventAdapter(mutableListOf())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    recyclerView.adapter = adapter

    swipeContainer.setOnRefreshListener {
      adapter.clear()
      viewModel.fetchEvents("RxKotlin")
    }

    viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    viewModel.eventLiveData.observe(this, Observer { events ->
      adapter.updateEvents(events)
      swipeContainer.isRefreshing = false
    })

    viewModel.fetchEvents("RxKotlin")
  }
}
