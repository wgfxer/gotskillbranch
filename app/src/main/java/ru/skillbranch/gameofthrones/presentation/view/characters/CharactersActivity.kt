package ru.skillbranch.gameofthrones.presentation.view.characters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.annotation.ColorRes
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.domain.CharactersInteractorImpl
import ru.skillbranch.gameofthrones.models.domain.HOUSES_ORDERED
import ru.skillbranch.gameofthrones.models.domain.HouseName
import ru.skillbranch.gameofthrones.presentation.view.GameOfThronesApplication
import ru.skillbranch.gameofthrones.presentation.viewmodel.characters.CharactersViewModel
import ru.skillbranch.gameofthrones.utils.RxSchedulersImpl
import ru.skillbranch.gameofthrones.utils.ViewModelProviderFactory

/**
 * Активити со списком персонажей по домам
 */
class CharactersActivity : AppCompatActivity() {

    private lateinit var viewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses)

        setupViewModel()
        setupToolbar()
        setupViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView: SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setFilter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupViewModel() {
        val gameOfThronesDao = (application as GameOfThronesApplication).db.getGameOfThronesDao()
        val rxSchedulers = RxSchedulersImpl()
        val charactersInteractor = CharactersInteractorImpl(gameOfThronesDao)
        viewModel = ViewModelProvider(this, ViewModelProviderFactory {
            CharactersViewModel(
                charactersInteractor,
                rxSchedulers
            )
        })[CharactersViewModel::class.java]
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupViewPager() {
        val viewPager: ViewPager2 = findViewById(R.id.houses_view_pager)
        val animationView = findViewById<TabLayoutAnimationView>(R.id.animated_color_view)
        viewPager.adapter = HousesAdapter(this)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view = tab?.view ?: return
                val location = IntArray(2)
                view.getLocationOnScreen(location)
                val centerHorizontal = location[0] + (view.width / 2f)
                val centerVertical = location[1] + (view.height / 2f)
                val color = getColor(HOUSES_ORDERED[tab.position].colorRes)
                val animationData = TabLayoutAnimationView.AnimationData(color, centerHorizontal, centerVertical)
                animationView.animateColoredCircle(animationData)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = HOUSES_ORDERED[position].shortName
        }.attach()
    }
}