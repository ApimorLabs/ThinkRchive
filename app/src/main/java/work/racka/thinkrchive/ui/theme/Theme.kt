package work.racka.thinkrchive.ui.theme

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.material.icons.outlined.Wallpaper
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColors(
    primary = YellowDefault,
    onPrimary = YellowDefaultDarkerOn,
    primaryVariant = YellowDefaultVariant,
    secondary = YellowDefault,
    onSecondary = YellowDefaultDarkerOn,
    secondaryVariant = YellowDefaultVariant,
    background = Color.Black,
    onBackground = LightGrayBackground,
    surface = LightDark,
    onSurface = LightGrayTextDark
)

private val LightColorPalette = lightColors(
    primary = YellowDefault,
    onPrimary = YellowDefaultDarkerOn,
    primaryVariant = YellowDefaultVariant,
    secondary = YellowDefault,
    onSecondary = YellowDefaultDarkerOn,
    secondaryVariant = YellowDefaultVariant,
    background = LightGrayBackground,
    onBackground = LightDark,
    onSurface = LightGrayText
)

private val AppLightColorScheme = lightColorScheme(
    primary = YellowDefault,
    onPrimary = YellowDefaultDarkerOn,
    tertiary = YellowDefaultVariant,
    onTertiary = YellowDefaultDarkerOn,
    secondary = YellowDefault,
    onSecondary = YellowDefaultDarkerOn,
    background = LightGrayBackground,
    onBackground = LightDark,
    onSurface = LightGrayText
)

private val AppDarkColorScheme = darkColorScheme(
    primary = YellowDefault,
    onPrimary = YellowDefaultDarkerOn,
    tertiary = YellowDefaultVariant,
    onTertiary = YellowDefaultDarkerOn,
    secondary = YellowDefault,
    onSecondary = YellowDefaultDarkerOn,
    background = Color.Black,
    onBackground = LightGrayBackground,
    surface = LightDark,
    onSurface = LightGrayTextDark
)

@Composable
fun ThinkRchiveTheme(
    theme: Int = Theme.FOLLOW_SYSTEM.themeValue,
    content: @Composable () -> Unit
) {
    val autoColors = if (isSystemInDarkTheme()) AppDarkColorScheme else AppLightColorScheme

    val dynamicColors = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else autoColors

    val colors = when (theme) {
        1 -> AppLightColorScheme
        2 -> AppDarkColorScheme
        12 -> dynamicColors
        else -> autoColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        //shapes = Shapes
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides ThinkRchiveRippleTheme,
            content = content
        )
    }
}

/**
 * Changing the ripple color for the whole app onClicks.
 * To be used as a value in CompositionalLocalProvider
 * as LocalRippleTheme provides ThinkRchiveRippleTheme
 * */
private object ThinkRchiveRippleTheme: RippleTheme {
    @Composable
    override fun defaultColor(): Color = MaterialTheme.colorScheme.primary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.Black,
        lightTheme = !isSystemInDarkTheme()
    )

}

// To be used to set the preferred theme inside settings
enum class Theme(
    val themeName: String,
    val icon: ImageVector,
    val themeValue: Int
) {
    MATERIAL_YOU(
        themeName = "Material You",
        icon = Icons.Outlined.Wallpaper,
        themeValue = 12
    ),
    FOLLOW_SYSTEM(
        themeName = "Follow System Settings",
        icon = Icons.Outlined.SettingsSuggest,
        themeValue = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    ),
    LIGHT_THEME(
        themeName = "Light Theme",
        icon = Icons.Outlined.LightMode,
        themeValue = AppCompatDelegate.MODE_NIGHT_NO
    ),
    DARK_THEME(
        themeName = "Dark Theme",
        icon = Icons.Outlined.DarkMode,
        themeValue = AppCompatDelegate.MODE_NIGHT_YES
    );
}
