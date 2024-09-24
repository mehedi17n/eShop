package com.example.eshop.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eshop.R
import com.example.eshop.data.categories.SubCategory
import com.example.eshop.data.products.Product

class SubCategoryAdapter(
    private var subcategoryProd: List<SubCategory?>?,
    private val onItemClick: (Product) -> Unit
):
    RecyclerView.Adapter<SubCategoryAdapter.ChildViewHolder>() {

    class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemName: TextView = view.findViewById(R.id.subCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sub_category, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val product = subcategoryProd?.get(position)
        holder.itemName.text = product?.name
    }

    override fun getItemCount(): Int {
        return subcategoryProd?.size ?: 0
    }

    fun updateList(response: List<SubCategory>) {
        subcategoryProd = response
        notifyDataSetChanged()
    }
}