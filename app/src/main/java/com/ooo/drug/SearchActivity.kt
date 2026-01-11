package com.ooo.drug

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val emptyMessage = findViewById<View>(R.id.emptyBox)
        val recentChipGroup = findViewById<ChipGroup>(R.id.recentChipGroup)
        val searchInput = findViewById<EditText>(R.id.searchInput)
        val backIcon = findViewById<ImageView>(R.id.backIcon)

        // 🔙 뒤로가기
        backIcon.setOnClickListener {
            finish()
        }

        // ⌨️ 200ms 후 키보드 자동 표시
        searchInput.postDelayed({
            searchInput.requestFocus()
            searchInput.setSelection(searchInput.text.length)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT)
        }, 300)

        // 📌 SharedPreferences에서 검색기록 불러오기
        val recentKeywords = loadKeywords().toMutableList()

        if (recentKeywords.isEmpty()) {
            emptyMessage.visibility = View.VISIBLE
            recentChipGroup.visibility = View.GONE
        } else {
            emptyMessage.visibility = View.GONE
            recentChipGroup.visibility = View.VISIBLE

            recentKeywords.forEach { keyword ->
                val chip = Chip(this).apply {
                    text = keyword
                    isCloseIconVisible = true
                    textSize = 13f

                    setTextColor(Color.parseColor("#333333"))
                    closeIconTint = ColorStateList.valueOf(Color.parseColor("#999999"))
                    chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#F4F3F3"))
                    chipStrokeWidth = 0f

                    setOnCloseIconClickListener {
                        recentChipGroup.removeView(this)

                        // 삭제 후 저장
                        recentKeywords.remove(keyword)
                        saveKeywordList(recentKeywords)

                        if (recentKeywords.isEmpty()) {
                            recentChipGroup.visibility = View.GONE
                            emptyMessage.visibility = View.VISIBLE
                        }
                    }
                }

                recentChipGroup.addView(chip)
            }
        }
    }

    // ================================
    //      검색기록 저장 / 불러오기
    // ================================

    private fun loadKeywords(): List<String> {
        val prefs = getSharedPreferences("search_prefs", MODE_PRIVATE)
        val keywords = prefs.getStringSet("recent_keywords", emptySet()) ?: emptySet()
        return keywords.toList()
    }

    private fun saveKeyword(newKeyword: String) {
        val prefs = getSharedPreferences("search_prefs", MODE_PRIVATE)
        val existing = prefs.getStringSet("recent_keywords", emptySet())?.toMutableList() ?: mutableListOf()

        // 중복 제거하고 최신 순 정렬
        existing.remove(newKeyword)
        existing.add(0, newKeyword)

        // 최대 5개까지만 유지
        if (existing.size > 5) {
            existing.subList(5, existing.size).clear()
        }

        prefs.edit().putStringSet("recent_keywords", existing.toSet()).apply()
    }

    private fun saveKeywordList(list: List<String>) {
        val prefs = getSharedPreferences("search_prefs", MODE_PRIVATE)
        val trimmed = if (list.size > 5) list.subList(0, 5) else list
        prefs.edit().putStringSet("recent_keywords", trimmed.toSet()).apply()
    }
}
