package com.faltenreich.release.framework.android.architecture

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LiveDataFix<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        when (owner) {
            is DialogFragment -> super.observe(owner, observer)
            // https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808
            is Fragment -> super.observe(owner.viewLifecycleOwner, observer)
            else -> super.observe(owner, observer)
        }
    }
}
