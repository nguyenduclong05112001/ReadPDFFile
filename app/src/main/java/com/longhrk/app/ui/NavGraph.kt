package com.longhrk.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.longhrk.app.MainActivity
import com.longhrk.app.ui.event.NavEvent
import com.longhrk.app.ui.screen.HomeScreen
import com.longhrk.app.ui.screen.PDFOffLineScreen
import com.longhrk.app.ui.screen.PDFOnLineScreen
import com.longhrk.app.ui.screen.SettingScreen
import com.longhrk.app.ui.screen.SplashScreen
import com.longhrk.app.ui.viewmodel.AppViewModel

@Composable
fun NavGraph(eventHandler: EventHandler, navController: NavHostController) {
    val startDestination = NavTarget.Splash.route
    val activity = LocalContext.current as MainActivity

    val viewModel = hiltViewModel<AppViewModel>(activity)

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
                viewModel = viewModel,

                onSettingScreen = {
                    eventHandler.postNavEvent(
                        event = NavEvent.Action(
                            target = NavTarget.Setting
                        )
                    )
                },

                onPDFOnLineScreen = {
                    eventHandler.postNavEvent(
                        event = NavEvent.Action(
                            target = NavTarget.PDFOnLine
                        )
                    )
                },

                onPDFOffLineScreen = {
                    eventHandler.postNavEvent(
                        event = NavEvent.Action(
                            target = NavTarget.PDFOffLine
                        )
                    )
                },

                onBackPress = {
                    activity.finish()
                }
            )
        }

        composable(NavTarget.PDFOnLine.route) {
            PDFOnLineScreen(
                viewModel = viewModel,
                onBackPress = {
                    eventHandler.postNavEvent(
                        event = NavEvent.PopBackStack()
                    )
                },
            )
        }

        composable(NavTarget.PDFOffLine.route) {
            PDFOffLineScreen(
                viewModel = viewModel,
                onBackPress = {
                    eventHandler.postNavEvent(
                        event = NavEvent.PopBackStack()
                    )
                },
            )
        }

        composable(NavTarget.Setting.route) {
            SettingScreen(
                onBackScreen = {
                    eventHandler.postNavEvent(
                        event = NavEvent.PopBackStack()
                    )
                },
            )
        }
    }
}