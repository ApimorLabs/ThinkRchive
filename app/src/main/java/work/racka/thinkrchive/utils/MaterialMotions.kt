package work.racka.thinkrchive.utils

// Requires Compose 1.1.0-alpha02
// Best used with navigation animation transitions in Accompanist 0.17.0

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween

//TODO: Attempt to implement MaterialMotions & SharedElement transitions

@ExperimentalAnimationApi
fun SharedXAxisEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(500, delayMillis = 90, easing = LinearOutSlowInEasing)) +
            slideInHorizontally(
                initialOffsetX = { 30 },
                animationSpec = tween(durationMillis = 300)
            )
}

@ExperimentalAnimationApi
fun SharedXAxisExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(90, easing = FastOutLinearInEasing)) +
            slideOutHorizontally(
                targetOffsetX = { -30 },
                animationSpec = tween(durationMillis = 300)
            )
}

@ExperimentalAnimationApi
fun SharedYAxisEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(500, delayMillis = 90, easing = LinearOutSlowInEasing)) +
            slideInVertically(
                initialOffsetY = { 30 },
                animationSpec = tween(durationMillis = 300)
            )
}

@ExperimentalAnimationApi
fun SharedYAxisExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(90, easing = FastOutLinearInEasing)) +
            slideOutVertically(
                targetOffsetY = { -30 },
                animationSpec = tween(durationMillis = 300)
            )
}

@ExperimentalAnimationApi
fun SharedZAxisEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(500, delayMillis = 90, easing = LinearOutSlowInEasing)) +
            scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(durationMillis = 300)
            )
}

@ExperimentalAnimationApi
fun SharedZAxisVariantEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(60, delayMillis = 60, easing = LinearEasing)) +
            scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(durationMillis = 300)
            )
}

@ExperimentalAnimationApi
fun SharedZAxisExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(90, easing = FastOutLinearInEasing)) +
            scaleOut(
                targetScale = 1.1f,
                animationSpec = tween(durationMillis = 300)
            )
}

@ExperimentalAnimationApi
fun SharedZAxisVariantExitTransition(): ExitTransition {
    return scaleOut(
        targetScale = 1.1f,
        animationSpec = tween(durationMillis = 300)
    )
}

@ExperimentalAnimationApi
fun FadeThroughEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
            scaleIn(
                initialScale = 0.92f,
                animationSpec = tween(210, delayMillis = 90, easing = LinearOutSlowInEasing)
            )
}

@ExperimentalAnimationApi
fun FadeThroughExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(90, easing = FastOutLinearInEasing))
}