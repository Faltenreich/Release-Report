package com.faltenreich.release.framework.android.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.faltenreich.release.data.provider.ViewModelCreator
import kotlin.reflect.KClass

abstract class BaseActivity(
    @LayoutRes private val layoutRes: Int
) : AppCompatActivity(layoutRes), ViewModelCreator {

    override fun <T : ViewModel> createViewModel(clazz: KClass<T>): T {
        return ViewModelProviders.of(this).get(clazz.java)
    }

    override fun <T : ViewModel> createSharedViewModel(clazz: KClass<T>): T {
        return createViewModel(clazz)
    }
}
