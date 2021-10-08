package com.rtugeek.android.colorseekbardemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.rtugeek.android.colorseekbardemo.databinding.ActivityMainBinding
import com.rtugeek.android.colorseekbardemo.fragment.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

private val TAB_TITLES = arrayOf(
    R.string.color,
    R.string.alpha
)

class ColorSeekBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        view_pager.adapter = sectionsPagerAdapter
        view_pager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabs, view_pager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
    }
}