package ru.skillbranch.gameofthrones.presentation.view.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.models.domain.Character
import ru.skillbranch.gameofthrones.models.domain.HouseName
import ru.skillbranch.gameofthrones.utils.StringUtils

/**
 * Адаптер списка персонажей
 *
 * @author Valeriy Minnulin
 */
class CharactersAdapter(
    private val house: HouseName,
    diffCallback: DiffUtil.ItemCallback<Character>,
    private val listener: OnCharacterClickListener
): ListAdapter<Character, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            holder.bind(currentList[position])
        }
    }

    private inner class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val houseLogo: ImageView = itemView.findViewById(R.id.house_logo)
        private val characterName: TextView = itemView.findViewById(R.id.character_name)
        private val characterAliases: TextView = itemView.findViewById(R.id.character_aliases)

        /**
         *  Привязать персонажа [character] к текущему вьюхолдеру
         */
        fun bind(character: Character) {
            houseLogo.setImageResource(house.iconRes)
            characterName.text = character.name.let { if (it.isNotEmpty()) it else StringUtils.UNKNOWN }
            characterAliases.text = character.aliases
                .joinToString(" ● ")
                .let { if (it.isNotEmpty()) it else StringUtils.UNKNOWN }
            itemView.setOnClickListener { listener.onCharacterClick(character, house) }
        }
    }

    /**
     * Интерфейс слушателя на нажатие на персонажа
     */
    interface OnCharacterClickListener {
        /**
         * Вызывается при нажатии на персонажа [character] из дома [house]
         */
        fun onCharacterClick(character: Character, house: HouseName)
    }
}