package com.faltenreich.releaseradar.ui.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.Rating
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.print
import com.faltenreich.releaseradar.setImageAsync
import com.faltenreich.releaseradar.tint
import com.faltenreich.releaseradar.ui.fragment.CalendarFragmentDirections
import kotlinx.android.synthetic.main.list_item_release.*

class ReleaseViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Release>(context, R.layout.list_item_release, parent) {
    override fun onBind(data: Release) {
        release_date.text = data.releaseDate?.print()
        release_name.text = data.artistName?.let { artist -> "$artist - ${data.name}" } ?: data.name
        release_description.text = data.description
        data.imageUrlForCover?.let { imageUrl -> release_cover.setImageAsync(imageUrl) }
        data.mediaType?.let { mediaType ->
            release_type.setImageResource(mediaType.iconResId)
            release_type.tint = ContextCompat.getColor(context, mediaType.colorResId)
        }
        data.rating?.let { rating ->
            release_rating.setBackgroundResource(Rating.valueOf(rating).colorResId)
            release_rating.text = rating.toString()
        }
        release_container.setOnClickListener {
            Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()
            openRelease(data)
        }
    }

    private fun openRelease(release: Release) {
        release.id?.let { id -> Navigation.findNavController(itemView).navigate(CalendarFragmentDirections.openRelease(id)) }
    }
}