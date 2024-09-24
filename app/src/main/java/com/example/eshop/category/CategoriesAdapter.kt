package com.example.eshop.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.data.categories.Data
import com.example.eshop.data.products.Product
import java.util.Locale

class CategoriesAdapter(
    private var categoryList: List<Data?>?,
    private val onProductClick: (Product) -> Unit

) :RecyclerView.Adapter<CategoriesAdapter.ParentViewHolder>() {

    class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productsCategory: TextView = view.findViewById(R.id.categoryName)
        var subItems: RecyclerView = view.findViewById(R.id.subItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        var view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {

        val category = categoryList?.get(position)

        holder.productsCategory.text = category?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }

        holder.subItems.layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL,false)
//        holder.subCategoryRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 3)
        holder.subItems.adapter = SubCategoryAdapter(category?.sub_categories, onProductClick)

//        holder.apply {
//            productsCategory.text = category.categoryName?.capitalize() ?:
//            childRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 3)
//            childRecyclerView.adapter = ChildAdapter(category.productList)
//        }

    }

    override fun getItemCount(): Int {
        return categoryList?.size ?: 0
    }

    // Function to update the adapter with new data
    fun updateList(response: List<Data?>?) {
        categoryList = response
        notifyDataSetChanged()
    }
}
