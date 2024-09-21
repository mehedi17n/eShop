package com.example.eshop.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.data.categories.Data
import java.util.Locale

class CategoriesAdapter(
    private var categoryList: List<Data>,
    private val onCategoryClick: (Data) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.categoryImage)
        val textView: TextView = view.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]

        // Set up click listener for the category
        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }

        // Bind the data to the views
        holder.textView.text = category.name // Directly access category name
        // Set an icon based on the category if needed
        // Example: holder.imageView.setImageResource(R.drawable.icon_for_category)
    }

    override fun getItemCount(): Int = categoryList.size

    // Method to update the list of categories (useful for refreshing the data)
    fun updateCategories(newCategories: List<Data>) {
        categoryList = newCategories
        notifyDataSetChanged() // Notify the adapter to refresh the view
    }
}
