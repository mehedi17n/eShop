package com.example.eshop.ui.home

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.eshop.R
import com.example.eshop.ui.fragments.FavouriteFragment
import com.example.eshop.ui.fragments.HomeFragment
import com.example.eshop.ui.fragments.PostFragment
import com.example.eshop.ui.fragments.ProfileFragment
import com.example.eshop.ui.fragments.SearchFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var home: ImageView
    private lateinit var search: ImageView
    private lateinit var favourite: ImageView
    private lateinit var profile: ImageView
    private lateinit var add: ImageView
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the views from custom bottom navigation
        home = findViewById(R.id.home)
        search = findViewById(R.id.search)
        favourite = findViewById(R.id.favourite)
        profile = findViewById(R.id.profile)
        add = findViewById(R.id.add)

        // Load the default fragment (Home)
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, homeFragment)
                .commit()
            activeFragment = homeFragment
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        // Home button click
        home.setOnClickListener {
            switchFragment(HomeFragment(), R.id.home)
        }

        // Search button click
        search.setOnClickListener {
            switchFragment(SearchFragment(), R.id.search)
        }

        // Favourite button click
        favourite.setOnClickListener {
            switchFragment(FavouriteFragment(), R.id.favourite)
        }

        // Profile button click
        profile.setOnClickListener {
            switchFragment(ProfileFragment(), R.id.profile)
        }

        // FloatingActionButton (FAB) click
        add.setOnClickListener {
            switchFragment(PostFragment(), R.id.add)
        }

        // Set default selected item
        updateNavBar(R.id.home)
    }

    private fun switchFragment(newFragment: Fragment, selectedItemId: Int) {
        // Replace fragment only if the new fragment is different
        if (activeFragment == null || newFragment::class != activeFragment!!::class) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, newFragment)
                commit()
            }
            activeFragment = newFragment
            updateNavBar(selectedItemId)
        } else {
            Toast.makeText(this, "Already on the selected page", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNavBar(selectedItemId: Int) {
        // Reset all icons to default state
        resetNavBarIcons()

        // Change the icon and background for the selected item
        when (selectedItemId) {
            R.id.home -> {
                home.apply {
                    setImageResource(R.drawable.ic_home_border)
                    setColorFilter(ContextCompat.getColor(context, R.color.primaryColor), PorterDuff.Mode.SRC_IN)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.search -> {
                search.apply {
                    setImageResource(R.drawable.ic_search_border)
                    setColorFilter(ContextCompat.getColor(context, R.color.primaryColor), PorterDuff.Mode.SRC_IN)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.favourite -> {
                favourite.apply {
                    setImageResource(R.drawable.ic_favorite_border)
                    setColorFilter(ContextCompat.getColor(context, R.color.primaryColor), PorterDuff.Mode.SRC_IN)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.profile -> {
                profile.apply {
                    setImageResource(R.drawable.ic_profile_border)
                    setColorFilter(ContextCompat.getColor(context, R.color.primaryColor), PorterDuff.Mode.SRC_IN)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
            R.id.add -> {
                add.apply {
                    setImageResource(R.drawable.ic_add_border)
                    setColorFilter(ContextCompat.getColor(context, R.color.primaryColor), PorterDuff.Mode.SRC_IN)
                    setBackgroundResource(R.drawable.circle_background)
                }
            }
        }
    }

    private fun resetNavBarIcons() {
        home.apply {
            setImageResource(R.drawable.ic_home_border)
            clearColorFilter()
            background = null
        }
        search.apply {
            setImageResource(R.drawable.ic_search_border)
            clearColorFilter()
            background = null
        }
        favourite.apply {
            setImageResource(R.drawable.ic_favorite_border)
            clearColorFilter()
            background = null
        }
        profile.apply {
            setImageResource(R.drawable.ic_profile_border)
            clearColorFilter()
            background = null
        }
        add.apply {
            setImageResource(R.drawable.ic_add_border)
            clearColorFilter()
            background = null
        }
    }
}