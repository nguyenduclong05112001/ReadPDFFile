package com.longhrk.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.longhrk.app.ui.EventHandler
import com.longhrk.app.ui.NavGraph
import com.longhrk.app.ui.extensions.handleNavEvent
import com.longhrk.app.ui.theme.Base_CodeTheme
import com.longhrk.app.ui.viewmodel.NavViewModel

class MainActivity : ComponentActivity() {
    private val navigationViewModel by viewModels<NavViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val eventHandler = remember {
                EventHandler(navigationViewModel)
            }
            Base_CodeTheme(darkTheme = true) {
                GraphMainApp(eventHandler)
            }
        }
    }
}

@Composable
private fun GraphMainApp(eventHandler: EventHandler) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        eventHandler.navEvent().collect {
            navController.handleNavEvent(it)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        NavGraph(eventHandler = eventHandler, navController = navController)
    }
}
