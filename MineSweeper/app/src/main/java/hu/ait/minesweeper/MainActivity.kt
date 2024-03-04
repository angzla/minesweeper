package hu.ait.mineSweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import hu.ait.mineSweeper.databinding.ActivityMainBinding
import hu.ait.mineSweeper.view.mineSweeperView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClear.setOnClickListener {
            binding.mineSweeperView.resetGame()
        }
    }

    public fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    public fun isFlagModeOn() : Boolean {
        return binding.cbFlag.isChecked
    }
}