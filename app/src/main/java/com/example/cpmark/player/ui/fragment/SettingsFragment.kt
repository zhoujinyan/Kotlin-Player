package com.example.cpmark.player.ui.fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cpmark.player.R

/**
 * Created by cpMark on 2017/9/30.
 */
class SettingsFragment :PreferenceFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        addPreferencesFromResource(R.xml.fragment_settings)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}