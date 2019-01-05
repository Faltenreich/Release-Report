package com.faltenreich.releaseradar.data

import com.faltenreich.releaseradar.data.model.Artist
import org.junit.Assert.assertNotNull
import org.junit.Test

class ArtistTest {

    private val artist by lazy { Artist().apply { title = "Artist" } }

    @Test
    fun `Validates if non-null`() {
        assertNotNull(artist)
    }
}