package com.alvindizon.meldcxtest.features.browser

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alvindizon.meldcxtest.R
import com.alvindizon.meldcxtest.core.Const
import com.alvindizon.meldcxtest.databinding.ActivityBrowserBinding
import com.alvindizon.meldcxtest.di.InjectorUtils
import com.alvindizon.meldcxtest.features.history.HistoryActivity
import com.bumptech.glide.Glide
import java.io.File
import javax.inject.Inject

class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowserBinding

    private lateinit var viewModel: BrowserViewModel

    @Inject lateinit var viewModelFactory: BrowserViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorUtils.getAppComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_browser)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BrowserViewModel::class.java)

        observeDb()

        setupToolbarButtons()

        setupWebView()
    }

    private fun observeDb() {
        viewModel.dbState?.observe(this, Observer {
            when(it) {
                SAVING_TO_DB -> Toast.makeText(this@BrowserActivity, R.string.ss_captured_saving, Toast.LENGTH_SHORT).show()
                SAVE_SUCCESS -> Toast.makeText(this@BrowserActivity, R.string.db_success, Toast.LENGTH_SHORT).show()
                is ERROR -> Toast.makeText(this@BrowserActivity, "Error: ${it.error}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == Const.BROWSER_ACTIVITY_CODE && data != null) {
                binding.webview.visibility = View.GONE
                binding.historyImage.visibility = View.VISIBLE

                val filename: String = data.getStringExtra(Const.FILENAME_KEY)

                Glide.with(binding.historyImage)
                    .load(File(this.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE), filename))
                    .centerCrop()
                    .into(binding.historyImage)

                val url = data.getStringExtra(Const.URL_KEY)
                binding.browserToolbar.editUrl.setText(url)
                binding.webview.loadUrl(url)
            }
        }
    }

    private fun setupToolbarButtons() {
        binding.browserToolbar.loadUrl.setOnClickListener {
            val url = binding.browserToolbar.editUrl.text.toString()
            !url.isBlank().apply {
                binding.webview.loadUrl(url)
            }
        }

        binding.browserToolbar.capture.setOnClickListener {
            val url = binding.browserToolbar.editUrl.text.toString()
            !url.isBlank().apply {
                val bitmap = createBitmapFromWebview(binding.webview)
                viewModel.saveBitmapAndCreateRecord(url, bitmap)
            }
        }

        binding.browserToolbar.history.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivityForResult(intent, Const.BROWSER_ACTIVITY_CODE)
        }
    }

    private fun createBitmapFromWebview(webView: WebView) : Bitmap {
        val bitmap =
            Bitmap.createBitmap(webView.width, webView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        webView.draw(canvas)
        return bitmap
    }

    private fun setupWebView() {
        binding.webview.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.progress = newProgress
                if(newProgress == 100) {
                    binding.webview.visibility = View.VISIBLE
                    binding.historyImage.visibility = View.GONE
                }
            }
        }
    }
}