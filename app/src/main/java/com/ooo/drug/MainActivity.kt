package com.ooo.drug

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 하단 네비게이션 뷰
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_home // 홈 탭 기본 선택

        // 탭 클릭 이벤트 처리
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // 현재 홈 화면이므로 별도 동작 없음
                    true
                }

                R.id.nav_history -> {
                    // 검색기록 → SearchActivity로 이동
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_medicine -> {
                    // 나의 약 → 추후 구현
                    // TODO: 나의 약 기능 구현 예정
                    true
                }

                else -> false
            }
        }

        // 검색창 클릭 시 SearchActivity로 이동
        val searchCard = findViewById<View>(R.id.searchCard)
        searchCard.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        searchEditText.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

    }
}
