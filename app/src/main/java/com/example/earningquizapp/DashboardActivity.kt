package com.example.earningquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.BuildCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.earningquizapp.databinding.ActivityDashboardBinding
import com.example.earningquizapp.fragments.WithdrawalFragment
import com.example.earningquizapp.viewmodel.CustomViewModelFactory
import com.example.earningquizapp.viewmodel.SpinViewModel
import com.example.earningquizapp.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth

class DashboardActivity : AppCompatActivity() {

    private var nvDrawer: NavigationView? = null
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null

    private lateinit var navController: NavController

    private val binding: ActivityDashboardBinding by lazy {
        ActivityDashboardBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, CustomViewModelFactory(this))[UserViewModel::class.java]
    }

    private val spinViewModel by lazy {
        ViewModelProvider(this)[SpinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initialization()

        openNav(savedInstanceState)
        navClick()

        //Bottom Navigation Bar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        //setupWithNavController(bottomNavigationView, navController)

        binding.ivBrCoin.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(this.supportFragmentManager, "test")
            bottomSheetDialog.enterTransition
        }

        binding.tvBrCoinAmount.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(this.supportFragmentManager, "test")
            bottomSheetDialog.enterTransition
        }

        viewModel.getUser()
        viewModel.userInfoResponse.observe(this) { user ->
            binding.tvBrName.text = user?.name
        }

        spinViewModel.getSpinData()
        spinViewModel.spinInfoResponse.observe(this) { spin ->
            binding.tvBrCoinAmount.text = spin?.coin.toString()
        }

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */) {
            finish()
        }
    }

    private fun initialization() {
        nvDrawer = findViewById(R.id.nv_drawer)
        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)
    }

    private fun openNav(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)

        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)

        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            nvDrawer!!.setCheckedItem(R.id.drawerLayout)
        }
    }

    private fun navClick() {
        /*
        // for menu
        nvDrawer!!.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_logout -> Toast.makeText(this, "Your are logout", Toast.LENGTH_SHORT)
                    .show()
            }
            drawerLayout!!.closeDrawer(GravityCompat.START)
            true
        }*/

        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "Your are logout", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    fun openDrawer() {
        drawerLayout!!.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer(drawerLayout: DrawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout!!)
    }
}