package com.example.timefighter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.timefighter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.apply {
            mainViewModel = viewModel
            lifecycleOwner = this@MainActivity
        }

        init()

        viewModel.isGameStarted.observe(this) { started ->
            if (started) {
                binding!!.button.setOnClickListener { viewModel.incrementScore() }
            } else {
                binding!!.button.setOnClickListener { viewModel.startGame() }
            }
        }

        viewModel.isTimeUp.observe(this) { timeUp ->
            if (timeUp) {
                showToast()
                viewModel.endGame()
            }
        }
    }

    private fun init() {
        binding!!.tvScore.text = getString(R.string.score, viewModel.score.value)
        binding!!.tvTimeLeft.text = getString(R.string.score, viewModel.timeLeft.value)
    }

    private fun showToast() {
        Toast.makeText(
            this,
            getString(R.string.time_up, viewModel.score.value),
            Toast.LENGTH_SHORT
        ).show()
    }

}