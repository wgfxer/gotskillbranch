package ru.skillbranch.gameofthrones.presentation.view.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.domain.CharactersInteractorImpl
import ru.skillbranch.gameofthrones.domain.HouseInteractorImpl
import ru.skillbranch.gameofthrones.models.domain.Character
import ru.skillbranch.gameofthrones.models.domain.HouseName
import ru.skillbranch.gameofthrones.presentation.view.GameOfThronesApplication
import ru.skillbranch.gameofthrones.presentation.view.detail.CharacterInfoActivity
import ru.skillbranch.gameofthrones.presentation.viewmodel.characters.CharactersViewModel
import ru.skillbranch.gameofthrones.utils.CharacterInfoLauncher
import ru.skillbranch.gameofthrones.utils.RxSchedulersImpl
import ru.skillbranch.gameofthrones.utils.StringUtils
import ru.skillbranch.gameofthrones.utils.ViewModelProviderFactory

/**
 * Фрагмент для персонажей одного дома
 *
 * @author Valeriy Minnulin
 */
class CharactersFragment: Fragment() {

    private lateinit var charactersRecyclerView: RecyclerView
    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var house: HouseName

    private val characterLauncher = CharacterInfoLauncher()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.house_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        house = arguments?.getSerializable(HOUSE_KEY) as? HouseName ?: HouseName.STARK
        charactersRecyclerView = view.findViewById(R.id.characters_recycler)
        charactersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        charactersAdapter = CharactersAdapter(house, CharactersDiffCallback(), getCharacterClickListener())
        charactersRecyclerView.adapter = charactersAdapter
        charactersRecyclerView.addItemDecoration(DividerItemDecoration())
        val characterDao = (requireActivity().application as GameOfThronesApplication).db.getCharacterDao()
        val rxSchedulers = RxSchedulersImpl()
        val charactersInteractor = CharactersInteractorImpl(characterDao)
        charactersViewModel = ViewModelProvider(requireActivity(), ViewModelProviderFactory {
            CharactersViewModel(
                charactersInteractor,
                rxSchedulers
            )
        })[CharactersViewModel::class.java]
        setupObservers()
    }

    private fun setupObservers() {
        charactersViewModel.getCharactersForHouse(house).observe(viewLifecycleOwner) {
            charactersAdapter.submitList(it)
        }
    }

    private fun getCharacterClickListener(): CharactersAdapter.OnCharacterClickListener {
        return object : CharactersAdapter.OnCharacterClickListener {
            override fun onCharacterClick(character: Character, house: HouseName) {
                characterLauncher.startCharacterInfo(character.id, house, requireActivity())
            }
        }
    }

    private class CharactersDiffCallback: DiffUtil.ItemCallback<Character>() {

        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val HOUSE_KEY = "HOUSE_KEY"

        /**
         * Получить новый экземпляр фрагмента со списком персонажей дома [house]
         */
        fun newInstance(house: HouseName): CharactersFragment{
            val args = Bundle()
            args.putSerializable(HOUSE_KEY, house)

            val fragment = CharactersFragment()
            fragment.arguments = args
            return fragment
        }
    }

}