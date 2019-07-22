package com.faltenreich.release.ui.list.decoration

import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.release.ui.list.adapter.ListAdapter

abstract class LinearLayoutItemDecoration<ITEM : Any, ADAPTER : ListAdapter<ITEM>> : ItemDecoration<ITEM, ADAPTER, LinearLayoutManager>()