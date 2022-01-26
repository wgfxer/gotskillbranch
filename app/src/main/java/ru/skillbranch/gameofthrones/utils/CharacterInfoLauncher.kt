package ru.skillbranch.gameofthrones.utils

import android.app.Activity
import ru.skillbranch.gameofthrones.models.domain.HouseName
import ru.skillbranch.gameofthrones.presentation.view.detail.CharacterInfoActivity

/**
 * @author Valeriy Minnulin
 */
class CharacterInfoLauncher {

    fun startCharacterInfo(id: String, houseName: HouseName, activity: Activity) {
        activity.startActivity(CharacterInfoActivity.newIntent(activity, houseName, id))
    }
}