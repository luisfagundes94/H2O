package com.luisfagundes.h2o.core.common.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationLoader(
    modifier: Modifier = Modifier,
    animId: Int
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animId))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        modifier = modifier,
        contentScale = ContentScale.Fit,
        composition = composition,
        progress = { progress },
    )
}