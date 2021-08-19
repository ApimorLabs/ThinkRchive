package work.racka.thinkrchive.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = YellowDefault,
    onPrimary = YellowDefaultDarkerOn,
    primaryVariant = YellowDefaultVariant,
    secondary = YellowDefault,
    onSecondary = YellowDefaultDarkerOn,
    secondaryVariant = YellowDefaultVariant,
    background = DarkBackground,
    onBackground = LightGrayBackground,
    surface = DarkSurface,
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
    onBackground = DarkBackground,
    onSurface = LightGrayText
)

@Composable
fun ThinkRchiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
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
    override fun defaultColor(): Color = MaterialTheme.colors.primary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.Black,
        lightTheme = !isSystemInDarkTheme()
    )

}
