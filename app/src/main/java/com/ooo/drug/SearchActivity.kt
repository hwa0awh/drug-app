package com.ooo.drug
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.FlexWrap


class SearchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        lateinit var adapter: SearchKeywordAdapter



        val emptyMessage = findViewById<View>(R.id.emptyBox)
        val searchInput = findViewById<EditText>(R.id.searchInput)
        val searchButton = findViewById<ImageView>(R.id.searchButton)
        val backIcon = findViewById<ImageView>(R.id.backIcon)
        val recyclerView = findViewById<RecyclerView>(R.id.recentRecyclerView)


// 🔙 뒤로가기
        backIcon.setOnClickListener {
            finish()
        }


// ⌨️ 키보드 자동 표시
        Handler(Looper.getMainLooper()).postDelayed({
            searchInput.requestFocus()
            searchInput.setSelection(searchInput.text.length)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT)
        }, 300)


// 입력 상태에 따라 아이콘 변경
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchButton.setImageResource(
                    if (!s.isNullOrBlank()) R.drawable.search_bt_black else R.drawable.search_bt
                )
            }
            override fun afterTextChanged(s: Editable?) {}
        })


// 최근 검색어 불러오기
        val recentKeywords = loadKeywords().toMutableList()


        if (recentKeywords.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyMessage.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyMessage.visibility = View.GONE


            val adapter = SearchKeywordAdapter(recentKeywords) { removedKeyword ->
                recentKeywords.remove(removedKeyword)
                saveKeywordList(recentKeywords)
                adapter.notifyDataSetChanged()


                if (recentKeywords.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    emptyMessage.visibility = View.VISIBLE
                }
            }


            recyclerView.layoutManager = FlexboxLayoutManager(this).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
            recyclerView.adapter = adapter
        }
    }


    private fun loadKeywords(): List<String> {
        val prefs = getSharedPreferences("search_prefs", MODE_PRIVATE)
        val keywords = prefs.getStringSet("recent_keywords", emptySet()) ?: emptySet()
        return keywords.toList()
    }


    private fun saveKeywordList(list: List<String>) {
        val prefs = getSharedPreferences("search_prefs", MODE_PRIVATE)
        val trimmed = if (list.size > 5) list.subList(0, 5) else list
        prefs.edit().putStringSet("recent_keywords", trimmed.toSet()).apply()
    }

    private fun showSearchLimitDialog() { // 검색 최대
        val dialogView = layoutInflater.inflate(R.layout.search_limit, null)

        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.findViewById<View>(R.id.confirmButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}