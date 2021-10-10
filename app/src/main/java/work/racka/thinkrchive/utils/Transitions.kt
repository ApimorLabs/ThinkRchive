package work.racka.thinkrchive.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.window.SplashScreen
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween

// Enter transition when you navigate to a screen
@ExperimentalAnimationApi
fun scaleInEnterTransition() = scaleIn(
    initialScale = .9f,
    animationSpec = tween(300)
) + fadeIn(
    animationSpec = tween(300)
)

// Exit transition when you navigate to a screen
@ExperimentalAnimationApi
fun scaleOutExitTransition() = scaleOut(
    targetScale = 1.1f,
    animationSpec = tween(300)
) + fadeOut(
    animationSpec = tween(300)
)

// Enter transition of a screen when you pop to it
@ExperimentalAnimationApi
fun scaleInPopEnterTransition() = scaleIn(
    initialScale = 1.1f,
    animationSpec = tween(300)
) + fadeIn(
    animationSpec = tween(300)
)

// Exit transition of a screen you are popping from
@ExperimentalAnimationApi
fun scaleOutPopExitTransition() = scaleOut(
    targetScale = .9f,
    animationSpec = tween(300)
) + fadeOut(
    animationSpec = tween(300)
)

// SplashScreen Dismiss transition
@RequiresApi(Build.VERSION_CODES.S)
fun dismissSplashScreen(splashScreen: SplashScreen) {
    splashScreen.setOnExitAnimationListener { splashScreenView ->
        val scaleOutX = ObjectAnimator.ofFloat(
            splashScreenView.iconView,
            View.SCALE_X,
            splashScreenView.scaleX,
            1.2f,
            0.3f
        )
        val scaleOutY = ObjectAnimator.ofFloat(
            splashScreenView.iconView,
            View.SCALE_Y,
            splashScreenView.scaleY,
            1.2f,
            0.3f
        )
        val fadeOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.ALPHA,
            splashScreenView.alpha,
            0f
        )
        fadeOut.startDelay = 250L

        AnimatorSet().apply {
            playTogether(scaleOutX, scaleOutY, fadeOut)
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500L
            start()
        }

    }
}
