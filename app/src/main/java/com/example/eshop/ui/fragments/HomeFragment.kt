package com.example.eshop.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.category.CategoriesAdapter
import com.example.eshop.category.CategoryDetailsActivity
import com.example.eshop.data.categories.Data
import com.example.eshop.ui.home.ItemSpacingDecoration
import com.example.eshop.viewModel.CategoryViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoriesAdapter
    private lateinit var categoryRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        setupRecyclerView()
        observeCategories()
    }

    private fun setupRecyclerView() {
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observeCategories() {
        lifecycleScope.launch {
            categoryViewModel.categories.collect { categories ->
                if (categories.isNotEmpty()) {
                    categoryAdapter = CategoriesAdapter(categories) { category ->
                        navigateToCategoryDetails(category.name ?: "")
                    }
                    categoryRecyclerView.adapter = categoryAdapter
                }
            }
        }
    }

    private fun navigateToCategoryDetails(categoryName: String) {
        val intent = Intent(requireContext(), CategoryDetailsActivity::class.java).apply {
            putExtra("CATEGORY", categoryName)
        }
        startActivity(intent)
    }
}