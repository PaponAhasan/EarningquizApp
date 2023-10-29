package com.example.earningquizapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.earningquizapp.QuizActivity
import com.example.earningquizapp.databinding.CategoryitemBinding
import com.example.earningquizapp.model.Category

class CategoryAdapter(private val category: ArrayList<Category>, val context: Context?) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(var binding: CategoryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = CategoryitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = CategoryViewHolder(view)
        return viewHolder
    }

    override fun getItemCount() = category.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = category[position]

        holder.binding.ivItem.setImageResource(currentItem.image)
        holder.binding.tvItemTitle.text = currentItem.title

        holder.binding.categoryItem.setOnClickListener {
            /* val fragmentName = QuizFragment()
            val bundle = Bundle()
            bundle.putInt("categoryImg", currentItem.image)
            bundle.putString("categoryTitle", currentItem.title)
            fragmentName.arguments = bundle

            val fragmentManager =
                (context as FragmentActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragmentName)
                .addToBackStack(fragmentName.toString())
                .commit() */

            val intent = Intent(context, QuizActivity::class.java)
            intent.putExtra("categoryImg", currentItem.image)
            intent.putExtra("categoryTitle", currentItem.title)
            context?.startActivity(intent)
        }
    }
}