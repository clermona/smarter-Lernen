package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appdev.smarterlernen.databinding.ActivityMainBinding

class StackDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stack_detail)
        //initial binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}