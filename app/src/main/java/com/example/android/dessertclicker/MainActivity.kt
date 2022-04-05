

package com.example.android.dessertclicker

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.example.android.dessertclicker.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var enemy_HP:Int=100
    private var revenue = 0
    private var enemyKilled = 0

    private lateinit var binding: ActivityMainBinding

    data class Enemies(val imageId: Int, val points: Int, val startProductionAmount: Int)

    private val allEnemies = listOf(
        Enemies(R.drawable.enemy_1, 5, 0),
        Enemies(R.drawable.enemy_2, 10, 3),
        Enemies(R.drawable.enemy_3, 15, 5),
        Enemies(R.drawable.enemy_4, 30, 8),
        Enemies(R.drawable.enemy_4, 50, 10)
    )

    private var currentEnemy = allEnemies[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainChar.setImageResource(R.drawable.main_character)
        binding.enemy.setOnClickListener {
            onEnemyClicked()
        }

        binding.revenue = revenue
        binding.amountSold = enemyKilled

        showHP()
        binding.enemy.setImageResource(currentEnemy.imageId)
    }

    private fun heroAnimate() {
        Handler().postDelayed({
            mainChar.setImageResource(R.drawable.mc_punch)
        }, 200)
    }

    private fun heroStay() {
        mainChar.setImageResource(R.drawable.main_character)
    }

    private fun onEnemyClicked() {

       enemy_HP-=10
        showHP()

        if (enemy_HP<1){
            revenue += currentEnemy.points
            enemyKilled++
            binding.revenue = revenue
            binding.amountSold = enemyKilled
            showCurrentEnemy()
            Log.i("Kill","Enemy killed")
        }

        heroAnimate()
        heroStay()
    }

    private fun showCurrentEnemy() {
        var newEnemy = allEnemies[0]
        for (enemy in allEnemies) {
            if (enemyKilled >= enemy.startProductionAmount) {
                newEnemy = enemy
                enemy_HP=100
                showHP()
            }
            else break
        }

        if (newEnemy != currentEnemy) {
            currentEnemy = newEnemy
            binding.enemy.setImageResource(newEnemy.imageId)
        }
    }

    private fun showHP(){
        binding.HP.progress = enemy_HP
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, enemyKilled, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
    }

}

private fun ImageView.setImageDrawable(attack1: Int) {

}
