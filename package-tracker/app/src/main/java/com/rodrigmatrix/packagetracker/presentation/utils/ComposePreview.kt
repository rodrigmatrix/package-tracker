package com.rodrigmatrix.packagetracker.presentation.utils

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Preview(name = "Phone", device = Devices.PHONE, showSystemUi = true)
@Preview(name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = true)
@Preview(name = "Unfolded Foldable", device = Devices.FOLDABLE, showSystemUi = true)
@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Preview(name = "Desktop", device = Devices.DESKTOP, showSystemUi = true)
@Preview(name = "Phone Dark", device = Devices.PHONE, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Preview(name = "Phone - Landscape Dark",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = true, uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
@Preview(name = "Unfolded Foldable Dark", device = Devices.FOLDABLE, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Preview(name = "Tablet Dark", device = Devices.TABLET, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Preview(name = "Desktop Dark", device = Devices.DESKTOP, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
annotation class PreviewAllTypes