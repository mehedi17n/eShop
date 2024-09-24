package com.example.eshop.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.category.CategoriesAdapter
import com.example.eshop.product.ProductAdapter
import com.example.eshop.viewModel.CategoryViewModel
import com.example.eshop.viewModel.ProductViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    // Product and Category RecyclerViews and Adapters
    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoriesAdapter
    private lateinit var progressBar: ProgressBar

    // ViewModels
    private lateinit var productViewModel: ProductViewModel
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModels
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // Initialize Views
        productsRecyclerView = view.findViewById(R.id.productRecyclerView)
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        // Set Layout Managers
        productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Initialize Adapters
        productAdapter = ProductAdapter(emptyList())
        categoryAdapter = CategoriesAdapter(emptyList()) { category ->
            // Handle category click
            Log.d("Category Clicked", category.toString())
        }

        // Set Adapters
        productsRecyclerView.adapter = productAdapter
        categoryRecyclerView.adapter = categoryAdapter

        // Load data and observe ViewModels
        loadProducts()
        loadCategories()
    }

    private fun loadProducts() {
        lifecycleScope.launch {
            productViewModel.productResponseFlow.collect { response ->
                Log.d("HomeFragment", response.toString())
                val productList = response?.data?.data
                if (productList != null) {
                    productAdapter.updateList(productList)
                }
            }
        }
    }

    private fun loadCategories() {
        lifecycleScope.launch {
            categoryViewModel.categoryResponseFlow.collect { response ->
                Log.d("HomeFragment", response.toString())
                val categoryList = response?.data
                if (categoryList != null) {
                    categoryAdapter.updateList(categoryList)
                }
            }
        }
    }
}
