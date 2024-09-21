package com.example.eshop.category

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eshop.R

class CategoryDetailsActivity : AppCompatActivity() {

    private lateinit var categoryName: TextView
    private lateinit var categoryDescription: TextView
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category_details)

        category = intent.getStringExtra("CATEGORY")

        categoryName = findViewById(R.id.categoryName)
        categoryDescription = findViewById(R.id.categoryDescription)

        // Set the data to views
        categoryName.text = category
        categoryDescription.text = "Description or additional info about $category"
    }
}