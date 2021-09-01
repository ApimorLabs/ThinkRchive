package work.racka.thinkrchive.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

// Holds data for all the sorting options
// Used in ViewModel to set respective Sorting option
// Used in ThinkpadListScreen and it's composables to
// display and select the respective Sorting option
enum class Sort(
    val type: String,
    val icon: ImageVector,
    val sortValue: Int
) {
    ALPHABETICAL_ASC(
        type = "Alphabetical Order",
        icon = Icons.Outlined.SortByAlpha,
        sortValue = 0
    ),
    NEW_RELEASE_FIRST(
        type = "Newest Release First",
        icon = Icons.Outlined.Devices,
        sortValue = 1
    ),
    OLD_RELEASE_FIRST(
        type = "Oldest Release First",
        icon = Icons.Outlined.Inventory2,
        sortValue = 2
    ),
    LOW_PRICE_FIRST(
        type = "Lowest Price First",
        icon = Icons.Outlined.PriceChange,
        sortValue = 3
    ),
    HIGH_PRICE_FIRST(
        type = "Highest Price First",
        icon = Icons.Outlined.PriceChange,
        sortValue = 4
    )
}