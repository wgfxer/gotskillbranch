package ru.skillbranch.gameofthrones.presentation.view.characters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.skillbranch.gameofthrones.models.domain.HOUSES_ORDERED

/**
 * Адаптер для вьюпейджера с персонажами для разных домов
 *
 * @author Valeriy Minnulin
 */
class HousesAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return HOUSES_ORDERED.size
    }

    override fun createFragment(position: Int): Fragment {
        return CharactersFragment.newInstance(HOUSES_ORDERED[position])
    }

}