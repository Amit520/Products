package com.app.productapp.utils

object Constants {
    const val GRID_LAYOUT_MANAGER = 1
    const val LINEAR_LAYOUT_MANAGER = 2

    enum class LayoutManagerType {
        GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER
    }

    enum class MEDIA_OPTIONS(val index: Int) {
        CAMERA(1),
        GALLERY(2)
    }
}