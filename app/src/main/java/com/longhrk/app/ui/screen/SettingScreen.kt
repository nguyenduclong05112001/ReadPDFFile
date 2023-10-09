package com.longhrk.app.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longhrk.app.R
import com.longhrk.app.ui.components.HeaderApp
import com.longhrk.app.ui.components.OptionSetting
import com.longhrk.app.ui.theme.backgroundColor
import com.longhrk.app.ui.theme.textColor

@Composable
fun SettingScreen(onBackScreen: () -> Unit) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderApp(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth()
                .background(textColor.copy(0.1f))
                .padding(vertical = 10.dp, horizontal = 15.dp),
            icon = painterResource(id = R.drawable.ic_arrow_back),
            title = "Setting"
        ) { onBackScreen() }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            OptionSetting(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .background(textColor.copy(0.1f))
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                textContent = "Policy App"
            )

            OptionSetting(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .background(textColor.copy(0.1f))
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                textContent = "Setting App"
            )

            OptionSetting(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .background(textColor.copy(0.1f))
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                textContent = "Help"
            )

            Text(
                modifier = Modifier.padding(15.dp),
                text = "Let me know how I can do better!",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = textColor,
                )
            )

            OptionSetting(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .background(textColor.copy(0.1f))
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                textContent = "Privacy Policy"
            )

            Text(
                modifier = Modifier.padding(15.dp),
                text = "Terms of use",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = textColor,
                )
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Version: " + context.getVersionName(),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W700,
                    color = textColor,
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}

fun Context.getVersionName(): String {
    return this.packageManager.getPackageInfo(
        this.packageName,
        0
    ).versionName
}