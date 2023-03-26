package com.app.productapp.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.productapp.R
import com.app.productapp.databinding.ActivityMainBinding
import com.app.productapp.network.NetworkResponse
import com.app.productapp.ui.add.AddProductActivity
import com.app.productapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var productListAdapter: ProductListAdapter

    var mCurrentLayoutManagerType: Constants.LayoutManagerType? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
        setupAdapter()
        mainViewModel.getProductList()

        binding.ivSwitcher.setOnClickListener {
            if (mCurrentLayoutManagerType == Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER) {
                setRecyclerViewLayoutManager(Constants.LayoutManagerType.GRID_LAYOUT_MANAGER)
                binding.ivSwitcher.setImageResource(R.drawable.ic_list_view)
            } else if (mCurrentLayoutManagerType == Constants.LayoutManagerType.GRID_LAYOUT_MANAGER) {
                setRecyclerViewLayoutManager(Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER)
                binding.ivSwitcher.setImageResource(R.drawable.ic_grid_view)
            } else {
                binding.ivSwitcher.setImageResource(R.drawable.ic_grid_view)
            }
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddProductActivity::class.java))
        }
    }

    private fun setupAdapter() {
        productListAdapter = ProductListAdapter()
        mLayoutManager = LinearLayoutManager(this)
        mCurrentLayoutManagerType = Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType)
        binding.rvProductList.apply {
            layoutManager = mLayoutManager
            adapter = productListAdapter
        }
    }

    private fun setRecyclerViewLayoutManager(layoutManagerType: Constants.LayoutManagerType?) {
        var scrollPosition = 0
        val mRecyclerView = binding.rvProductList
        // Store scroll position of Recyclerview
        if (binding.rvProductList.layoutManager != null) {
            scrollPosition = (mRecyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }
        when (layoutManagerType) {
            Constants.LayoutManagerType.GRID_LAYOUT_MANAGER -> {
                mLayoutManager = GridLayoutManager(this@MainActivity, 2)
                mCurrentLayoutManagerType = Constants.LayoutManagerType.GRID_LAYOUT_MANAGER
                productListAdapter.mCurrentLayoutManagerType =
                    Constants.LayoutManagerType.GRID_LAYOUT_MANAGER
            }
            Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER -> {
                mLayoutManager = LinearLayoutManager(this@MainActivity)
                mCurrentLayoutManagerType = Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER
                productListAdapter.mCurrentLayoutManagerType =
                    Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
            else -> {
                mLayoutManager = LinearLayoutManager(this@MainActivity)
                mCurrentLayoutManagerType = Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER
                productListAdapter.mCurrentLayoutManagerType =
                    Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
        }
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.scrollToPosition(scrollPosition)
    }


    private fun setupObserver() {
        mainViewModel.response.observe(this) {
            when (it) {
                is NetworkResponse.Loading -> {
                    binding.progressCircular.isVisible = true
                    binding.rvProductList.isVisible = false
                }
                is NetworkResponse.Success -> {
                    binding.progressCircular.isVisible = false
                    binding.rvProductList.isVisible = true
                    productListAdapter.refreshData(it.data?.products ?: arrayListOf())
                }
                is NetworkResponse.Error -> {
                    binding.progressCircular.isVisible = false
                    binding.rvProductList.isVisible = false
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}