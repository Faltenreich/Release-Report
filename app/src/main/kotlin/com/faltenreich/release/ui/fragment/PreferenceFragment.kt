package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import com.faltenreich.release.R
import com.faltenreich.release.extension.className
import kotlinx.android.synthetic.main.fragment_preference.*

class PreferenceFragment : BaseFragment(R.layout.fragment_preference) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initList()
    }

    private fun initToolbar() {
        appCompatActivity?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initList() {
        fragmentManager?.beginTransaction()?.also { transaction ->
            val fragment = PreferenceListFragment()
            transaction.replace(R.id.container, fragment, fragment.className)
            transaction.commit()
        }
    }
}