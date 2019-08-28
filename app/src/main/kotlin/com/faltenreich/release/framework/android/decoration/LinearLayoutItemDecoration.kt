package com.faltenreich.release.framework.android.decoration

import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.release.framework.android.adapter.ListAdapter

abstract class LinearLayoutItemDecoration<ITEM : Any, ADAPTER : ListAdapter<ITEM>> : ItemDecoration<ITEM, ADAPTER, LinearLayoutManager>()