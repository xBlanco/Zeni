package com.zeni.settings.domain.model

import android.net.Uri

data class DevsInfo(
    val name: String,
    val image: Int,
    val github: Uri
) {
    constructor(name: String, image: Int, github: String) : this(
        name = name,
        image = image,
        github = Uri.parse(github)
    )
}
