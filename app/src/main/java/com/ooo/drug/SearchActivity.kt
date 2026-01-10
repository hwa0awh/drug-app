package com.ooo.drug

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SearchActivity : AppCompatActivity() {

    // 예시로 사용하는 최근 검색어 리스트
    private val recentKeywords = listOf("타이레놀", "이부프로펜", "모사피아정")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val emptyMessage = findViewById<View>(R.id.emptyBox)
        val recentChipGroup = findViewById<ChipGroup>(R.id.recentChipGroup)
        val searchInput = findViewById<EditText>(R.id.searchInput)

        // 200ms 후 키보드 자동 표시
        Handler(Looper.getMainLooper()).postDelayed({
            searchInput.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT)
        }, 200)

        // 검색어 유무에 따라 뷰 설정
        if (recentKeywords.isEmpty()) {
            emptyMessage.visibility = View.VISIBLE
            recentChipGroup.visibility = View.GONE
        } else {
            emptyMessage.visibility = View.GONE
            recentChipGroup.visibility = View.VISIBLE

            // 최근 검색어 Chip 동적 추가
            recentKeywords.forEach { keyword ->
                val chip = Chip(this).apply {
                    text = keyword
                    isCloseIconVisible = true
                    setOnCloseIconClickListener {
                        recentChipGroup.removeView(this)
                    }
                }
                recentChipGroup.addView(chip)
            }
        }

        // TODO: 약 리스트 및 카테고리 동적 처리도 여기에 추가
    }
}
