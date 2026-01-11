package com.ooo.drug

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

class SearchKeywordAdapter(
    private val keywords: List<String>,
    private val onRemoveKeyword: (String) -> Unit
) : RecyclerView.Adapter<SearchKeywordAdapter.KeywordViewHolder>() {

    inner class KeywordViewHolder(val chip: Chip) : RecyclerView.ViewHolder(chip)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val chip = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_keyword, parent, false) as Chip
        return KeywordViewHolder(chip)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        val keyword = keywords[position]
        holder.chip.text = keyword

        holder.chip.setOnCloseIconClickListener {
            onRemoveKeyword(keyword)
        }
    }

    override fun getItemCount(): Int = keywords.size
}
