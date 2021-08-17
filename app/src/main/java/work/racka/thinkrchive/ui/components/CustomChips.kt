package work.racka.thinkrchive.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


/**
 * TODO: Add Chips
 * This Chip has select state which can be checked (chipSelected = true) or
 * unchecked (chipSelected = false).
 * You can change the colors of chip and it's text in different states using
 * the provided chip color parameters.
 * onClick with provide the state you have from the chip
 * */
@Composable
fun CheckedChip(
    onClick: (state: Boolean) -> Unit = { },
    chipSelected: Boolean = false,
    chipDefaultBackgroundColor: Color = MaterialTheme.colors.surface,
    chipSelectedColor: Color = MaterialTheme.colors.primary,
    chipDefaultTextColor: Color = MaterialTheme.colors.onSurface,
    chipSelectedTextColor: Color = MaterialTheme.colors.onPrimary,
    modifier: Modifier = Modifier
) {
    var isChipSelected by remember {
        mutableStateOf(chipSelected)
    }


}