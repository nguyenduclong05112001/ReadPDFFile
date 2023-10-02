package com.longhrk.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.longhrk.app.MainActivity
import com.longhrk.app.ui.event.NavEvent
import com.longhrk.app.ui.screen.HomeScreen
import com.longhrk.app.ui.screen.OtherScreen
import com.longhrk.app.ui.screen.SplashScreen

@Composable
fun NavGraph(eventHandler: EventHandler, navController: NavHostController) {
    val startDestination = NavTarget.Splash.route
    val activity = LocalContext.current as MainActivity

    NavHost(navController, startDestination) {
        composable(NavTarget.Splash.route) {
            SplashScreen {
                eventHandler.postNavEvent(
                    event = NavEvent.ActionWithPopUp(
                        target = NavTarget.Home,
                        popupTarget = NavTarget.Splash,
                        inclusive = true
                    )
                )
            }
        }

        composable(NavTarget.Home.route) {
            HomeScreen(
                onNextScreen = {
                    eventHandler.postNavEvent(
                        event = NavEvent.Action(
                            target = NavTarget.Other
                        )
                    )
                },
                onBackPress = {
                    activity.finish()
                }
            )
        }

        composable(NavTarget.Other.route) {
            OtherScreen(
                onClick = { }
            )
        }
    }
}