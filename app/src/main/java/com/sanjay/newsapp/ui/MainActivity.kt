package com.sanjay.newsapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.newsapp.R
import com.sanjay.newsapp.data.model.Articles
import com.sanjay.newsapp.utils.Constants
import com.sanjay.newsapp.utils.Status
import com.sanjay.newsapp.viewmodel.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(),
    CoroutineScope {
    private val viewModel: NewsViewModel by viewModel()
    var adapter: NewsAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var mIsLoading = false
    private var mIsLastPage: Boolean = false
    private var searchView: SearchView? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var job: Job
    private lateinit var arrayList: List<Articles>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()
        initView()
    }

    private fun initView() {
        mIsLoading = false
        mIsLastPage = false
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    /*    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)*/
        arrayList = emptyList()

        CoroutineScope(coroutineContext).launch {
            setupObserver(
                Constants.API_KEY
            )
        }
    }

    private fun setupObserver(
        key: String
    ) {
        progressBar.visibility = View.VISIBLE
        viewModel.getNewsData(key).observe(this, {
            progressBar.visibility = View.INVISIBLE
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.body()?.articles?.let { it1 ->
                        arrayList = it1
                        setAdapterData(arrayList)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(
                        this,
                        it.message!!, Toast.LENGTH_LONG
                    ).show()
                }
                else ->
                    Toast.makeText(
                        this,
                        "Something went wrong !!", Toast.LENGTH_LONG
                    ).show()
            }
        })
    }

    private fun setAdapterData(articleList: List<Articles>) {
        adapter = NewsAdapter(this@MainActivity, articleList)
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val search = menu.findItem(R.id.action_search)
        searchView = search.actionView as SearchView
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                searchView!!.clearFocus()
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                val filteredList: MutableList<Articles> =
                    filter(arrayList, s.toLowerCase(Locale.ROOT))
                adapter?.setFilter(filteredList)
                return true
            }
        })
        return true
    }

    private fun filter(models: List<Articles>, query: String): MutableList<Articles> {
        val qString = query.toLowerCase(Locale.ROOT)
        val filteredList: MutableList<Articles> = ArrayList()
        for (model in models) {
            val source: String = model.source.name.toLowerCase(Locale.ROOT)
            if (source.contains(qString)) {
                filteredList.add(model)
            }
        }
        return filteredList
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}