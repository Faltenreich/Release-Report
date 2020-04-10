package com.faltenreich.release.framework.android.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes private val layoutRes: Int) : AppCompatActivity(layoutRes)