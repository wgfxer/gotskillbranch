package ru.skillbranch.gameofthrones.presentation.view.start

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.remote.GameOfThronesService
import ru.skillbranch.gameofthrones.domain.HouseInteractorImpl
import ru.skillbranch.gameofthrones.domain.converter.CharacterConverter
import ru.skillbranch.gameofthrones.domain.converter.HouseConverter
import ru.skillbranch.gameofthrones.presentation.view.GameOfThronesApplication
import ru.skillbranch.gameofthrones.presentation.view.characters.CharactersActivity
import ru.skillbranch.gameofthrones.presentation.viewmodel.start.StartViewModel
import ru.skillbranch.gameofthrones.utils.RxSchedulersImpl
import ru.skillbranch.gameofthrones.utils.ViewModelProviderFactory

class StartActivity : AppCompatActivity() {

    private lateinit var startViewModel: StartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val gameOfThronesApi = GameOfThronesService.create()
        val rxSchedulers = RxSchedulersImpl()
        val gameOfThronesDao = (application as GameOfThronesApplication).db.getGameOfThronesDao()
        val characterConverter = CharacterConverter()
        val houseInteractor = HouseInteractorImpl(gameOfThronesApi, characterConverter, gameOfThronesDao)
        startViewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory {
                StartViewModel(
                    houseInteractor,
                    rxSchedulers
                )
            }
        )[StartViewModel::class.java]

        setupObservers()

        startAnimation()
    }

    private fun setupObservers() {
        startViewModel.loadingErrorEvent.observe(this) {
            Snackbar.make(
                findViewById(R.id.start_activity_frame),
                "Произошла ошибка при загрузке из сети.",
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }

        startViewModel.goToNextScreenEvent.observe(this) {
            val intent = Intent(this, CharactersActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun startAnimation() {
        val frameLayout: FrameLayout = findViewById(R.id.splash_shadow_view)
        val valueAnimator = ValueAnimator.ofFloat(0f, 0.4f).apply {
            addUpdateListener { frameLayout.alpha = it.animatedValue as Float }
            repeatCount = INFINITE
            repeatMode = REVERSE
            duration = 800L
        }
        valueAnimator.start()
    }
}