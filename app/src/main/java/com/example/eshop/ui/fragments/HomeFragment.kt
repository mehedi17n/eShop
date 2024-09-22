package com.example.eshop.ui.fragments

import android.content.Intent
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
import com.example.eshop.category.CategoryDetailsActivity
import com.example.eshop.data.products.Product
import com.example.eshop.product.ProductAdapter
import com.example.eshop.ui.home.ItemSpacingDecoration
import com.example.eshop.viewModel.CategoryViewModel
import com.example.eshop.viewModel.ProductViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]


        productsRecyclerView = view.findViewById(R.id.productRecyclerView)


        productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter
        adapter = ProductAdapter(emptyList())

        // Set up RecyclerView
        productsRecyclerView.adapter = adapter


        lifecycleScope.launch {
            productViewModel.productResponseFlow.collect { response ->
                Log.d("HomeFragment", response.toString())
                val productList = response?.data?.data

                if (productList != null) {
                    adapter.updateList(productList)
                }
            }
        }

    }

}