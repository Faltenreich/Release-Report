# Release Report

[![Release](https://img.shields.io/badge/Release-0.2.1-006d8c.svg)](https://play.google.com/store/apps/details?id=com.faltenreich.release)
[![License](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

Release Report is an Android app that shows release dates for upcoming movies, music and video games.

## Development

#### Setup

1. Complete setup for [Release Report Sync](https://github.com/Faltenreich/Release-Report-Sync)
2. Clone or fork this repository
3. Open project via Android Studio
4. Create local.properties in the root directory with following properties from your [Parse Server](https://parseplatform.org) Core Settings:

    ```
    parse.serverUrl = "<Parse API Address>"
    parse.applicationId = "<App Id>"
    parse.clientKey = "<Client Key>"
    ```

5. Gradle Sync
6. Build

#### Third-party licenses

This software uses following technologies with great appreciation:

* [AndroidX](https://developer.android.com/jetpack/androidx)
* [Glide](https://github.com/bumptech/glide)
* [JUnit](https://junit.org)
* [Kotlin](https://kotlinlang.org)
* [LicensesDialog](https://github.com/PSDev/LicensesDialog)
* [Material Components for Android](https://material.io/components)
* [MonthAndYearPicker](https://github.com/premkumarroyal/MonthAndYearPicker)
* [Parse SDK for Android](https://github.com/parse-community/Parse-SDK-Android)
* [ScrollGalleryView](https://github.com/VEINHORN/ScrollGalleryView)
* [SearchView](https://github.com/lapism/SearchView)
* [SkeletonLayout](https://github.com/Faltenreich/SkeletonLayout)

These dependencies are bundled under the terms of their respective license.

## Legal

#### Redistribution

Additionally to the permissions, conditions and limitations of the GPLv3, the permission for redistribution must be manually requested in advance. This ensures that neither the original software or any fork will be affected negatively by terms and conditions like the [Google Play Developer Distribution Agreement](https://play.google.com/about/developer-distribution-agreement.html). If you plan to redistribute this software, please contact the maintainer at [philipp.fahlteich@gmail.com](mailto:philipp.fahlteich@gmail.com).

#### License

    Copyright (C) 2020 Philipp Fahlteich

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
