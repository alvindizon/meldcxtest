package com.alvindizon.meldcxtest.features.history

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvindizon.meldcxtest.R
import com.alvindizon.meldcxtest.core.Const
import com.alvindizon.meldcxtest.databinding.ActivityHistoryBinding
import com.alvindizon.meldcxtest.di.InjectorUtils
import javax.inject.Inject


class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    private lateinit var viewModel: HistoryViewModel

    private lateinit var adapter: HistoryAdapter

    @Inject lateinit var viewModelFactory: HistoryViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorUtils.getAppComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)

        setupDataBinding()

        setupRecyclerView()

        fetchAndDisplayData()

        setupToolbarButtons()
    }

    private fun setupDataBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter({
            val intent = Intent()
            intent.putExtra(Const.FILENAME_KEY, it.fileName)
            intent.putExtra(Const.URL_KEY, it.url)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }, viewModel)

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter
    }


    private fun fetchAndDisplayData() {
        viewModel.fetchData()

        viewModel.uiState?.observe(this, Observer {
            when(it) {
                // note: just using it.historyUiItemList won't work here. you need to pass a new list
                // in order for ListAdapter's diffcallback to take effect and for the animation to trigger during deletion.
                // hence, the call to toMutableList
                is SUCCESS -> adapter.submitList(it.historyUiItemList.toMutableList())
                is ERROR -> Toast.makeText(this@HistoryActivity, "Error: ${it.error}", Toast.LENGTH_SHORT).show()
                LOADING -> {}
            }
        })
    }

    private fun setupToolbarButtons() {
        binding.historyToolbar.search.setOnClickListener {
            val url = binding.historyToolbar.editUrl.text.toString()
            !url.isBlank().apply {
                viewModel.searchByUrl(url)
            }
        }
    }

}