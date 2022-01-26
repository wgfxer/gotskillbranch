package ru.skillbranch.gameofthrones.presentation.view.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.domain.CharactersInteractorImpl
import ru.skillbranch.gameofthrones.models.domain.Character
import ru.skillbranch.gameofthrones.models.domain.HouseName
import ru.skillbranch.gameofthrones.presentation.view.GameOfThronesApplication
import ru.skillbranch.gameofthrones.presentation.view.common.CharacterInfoField
import ru.skillbranch.gameofthrones.presentation.view.common.ParentInfoField
import ru.skillbranch.gameofthrones.presentation.viewmodel.detail.CharacterInfoViewModel
import ru.skillbranch.gameofthrones.utils.CharacterInfoLauncher
import ru.skillbranch.gameofthrones.utils.RxSchedulersImpl
import ru.skillbranch.gameofthrones.utils.StringUtils
import ru.skillbranch.gameofthrones.utils.ViewModelProviderFactory

class CharacterInfoActivity : AppCompatActivity() {

    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var toolbarBackground: ImageView

    private lateinit var wordsField: CharacterInfoField
    private lateinit var bornField: CharacterInfoField
    private lateinit var titlesField: CharacterInfoField
    private lateinit var aliasesField: CharacterInfoField

    private lateinit var characterInfoLayout: LinearLayout

    private lateinit var viewModel: CharacterInfoViewModel

    private val characterLauncher = CharacterInfoLauncher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_info)
        initViews()
        setupToolbar()
        setupViewModel()
        setThemeForHouse()
        setupObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        toolbarBackground = findViewById(R.id.toolbar_background)
        wordsField = findViewById(R.id.words_field)
        bornField = findViewById(R.id.born_field)
        titlesField = findViewById(R.id.titles_field)
        aliasesField = findViewById(R.id.aliases_field)
        characterInfoLayout = findViewById(R.id.character_info)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        val characterDao = (application as GameOfThronesApplication).db.getCharacterDao()
        val rxSchedulers = RxSchedulersImpl()
        val charactersInteractor = CharactersInteractorImpl(characterDao)
        viewModel = ViewModelProvider(this, ViewModelProviderFactory {
            CharacterInfoViewModel(
                charactersInteractor,
                rxSchedulers
            )
        })[CharacterInfoViewModel::class.java]
    }

    private fun setThemeForHouse() {
        val house = intent.getSerializableExtra(HOUSE_NAME_KEY) as? HouseName ?: return
        toolbarBackground.setImageResource(house.coastOfArmsRes)
        collapsingToolbar.setContentScrimResource(house.colorRes)

        wordsField.setIconColor(house.accentColorRes)
        bornField.setIconColor(house.accentColorRes)
        titlesField.setIconColor(house.accentColorRes)
        aliasesField.setIconColor(house.accentColorRes)
    }

    private fun setupObservers() {
        val characterId: String = intent.getStringExtra(CHARACTER_ID_KEY) ?: return
        viewModel.getCharacterLiveData(characterId).observe(this, ::setCharacterInfo)
        viewModel.parentsLiveData.observe(this, ::setParents)
    }

    private fun setCharacterInfo(character: Character) {
        collapsingToolbar.title = character.name
        bornField.setFieldContentText(character.born)
        titlesField.setFieldContentText(character.titles.joinToString())
        aliasesField.setFieldContentText(character.aliases.joinToString())
        wordsField.setFieldContentText(character.words)
        if (character.died.isNotEmpty()) {
            Snackbar.make(wordsField, "died in: ${character.died}", LENGTH_INDEFINITE).show()
        }

        if (character.mother.isNotEmpty() || character.father.isNotEmpty()) {
            viewModel.loadParents(character.mother, character.father)
        }
    }

    private fun setParents(parents: Pair<Character?, Character?>) {
        parents.first?.let { addParent(it.id, it.name, "Mother: ") }
        parents.second?.let { addParent(it.id, it.name, "Father: ") }
    }

    private fun addParent(parentId: String, parentName: String, parentType: String) {
        val view = ParentInfoField(this)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val house = intent.getSerializableExtra(HOUSE_NAME_KEY) as? HouseName ?: return
        view.setParentButtonColor(house.colorRes)
        view.setButtonClickListener {
            characterLauncher.startCharacterInfo(parentId, house, this)
        }
        view.setParentButtonText(parentName)
        view.setParentTypeText(parentType)
        characterInfoLayout.addView(view)
    }

    companion object {
        private const val HOUSE_NAME_KEY = "house_name"
        private const val CHARACTER_ID_KEY = "character_id"

        /**
         * Получить интент для перехода на эту активити
         */
        fun newIntent(context: Context, house: HouseName, characterId: String): Intent {
            val intent = Intent(context, CharacterInfoActivity::class.java)
            intent.putExtra(HOUSE_NAME_KEY, house)
            intent.putExtra(CHARACTER_ID_KEY, characterId)
            return intent
        }
    }
}