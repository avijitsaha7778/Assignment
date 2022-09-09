package com.avijit.avijitassignment

import android.content.res.AssetManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.avijit.avijitassignment.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImgListAdapter
    lateinit var imageData: ImageData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterView = ImageAdapter(this)
        binding.viewPage.adapter = adapterView

        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = ImgListAdapter(this)
        binding.recyclerView.adapter = adapter

        val gson = Gson()
        CoroutineScope(Dispatchers.IO).launch {
            val imageData =
                gson.fromJson(
                    this@MainActivity.assets.readAssetsFile("dataj.json"),
                    ImageData::class.java
                )
            withContext(Dispatchers.Main) {
                adapterView.addItem(imageData.data)
                binding.viewPage.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    }

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        adapter.addItem(imageData.data[position].clips)
                        binding.search.clearFocus()
                        binding.search.setText("")
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)

                    }
                })

                binding.search.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        adapter.filter?.filter(charSequence)
                    }

                    override fun afterTextChanged(editable: Editable) {}
                })
            }
        }


    }

    // extension fun by Avijit
    private fun AssetManager.readAssetsFile(fileName: String): String =
        open(fileName).bufferedReader().use { it.readText() }
}