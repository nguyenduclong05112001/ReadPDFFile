package com.longhrk.app.ui.screen

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.longhrk.app.R
import com.longhrk.app.ui.theme.backgroundColor
import com.longhrk.app.ui.theme.componentColor
import com.longhrk.app.ui.theme.itemInComponentColor
import com.longhrk.app.ui.theme.textColor
import com.longhrk.app.ui.viewmodel.AppViewModel
import jp.wasabeef.blurry.Blurry

@SuppressLint("Recycle")
@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    onPDFOnLineScreen: () -> Unit,
    onPDFOffLineScreen: () -> Unit,
    onBackPress: () -> Unit
) {
    val notifyBackOutApp = stringResource(id = R.string.notify_back_out_app)
    val context = LocalContext.current
    var backPressTime = 0L
    val view = LocalView.current
    var valueOfTextField by remember {
        mutableStateOf("")
    }
    val statusInputNetwork by viewModel.showInputNetWorkLink.collectAsState()

    var uriPDF by remember {
        mutableStateOf(Uri.EMPTY)
    }

    val launcherGetPDFFile = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uriPDF = uri
        }
    )

    LaunchedEffect(key1 = uriPDF, block = {
        if (uriPDF != Uri.EMPTY) {
            viewModel.updateUriPDF(uriPDF)
            onPDFOffLineScreen()
        }
    })

    BackHandler {
        if (backPressTime + 1500 > System.currentTimeMillis()) {
            onBackPress()
        } else {
            Toast.makeText(context, notifyBackOutApp, Toast.LENGTH_LONG).show()
        }
        backPressTime = System.currentTimeMillis()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (statusInputNetwork) {
                    viewModel.updateStateInputNetWork(false)
                }
            }
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonMain(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20))
                        .clickable {
                            viewModel.updateStateInputNetWork(true)
                        }
                        .weight(1f)
                        .background(componentColor),
                    content = "Online PDF",
                    icon = R.drawable.ic_online
                )

                Spacer(modifier = Modifier.width(20.dp))

                ButtonMain(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20))
                        .clickable {
                            launcherGetPDFFile.launch("application/pdf")
                        }
                        .weight(1f)
                        .background(componentColor),
                    content = "Local PDF",
                    icon = R.drawable.ic_folder
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        if (statusInputNetwork) {
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { factoryContext ->
                        ImageView(factoryContext).apply {
                            Blurry
                                .with(factoryContext)
                                .capture(view)
                                .into(this)
                            setColorFilter(Color.parseColor("#7F000000"))
                        }
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                        .background(backgroundColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Get PDF File Online.",
                        style = TextStyle(
                            fontWeight = FontWeight.W700,
                            fontSize = 24.sp,
                            color = textColor
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = textColor,
                            textColor = textColor,
                            unfocusedBorderColor = textColor,
                            focusedBorderColor = textColor,
                            placeholderColor = textColor.copy(0.5f)
                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400
                        ),
                        placeholder = { Text(text = "Enter your link here") },
                        singleLine = true,
                        maxLines = 1,
                        value = valueOfTextField,
                        onValueChange = {
                            valueOfTextField = it
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(2f))

                        Button(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = componentColor,
                            ),
                            onClick = {
                                if (valueOfTextField.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Enter your link of PDF file, please",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    viewModel.updateLinkFilePDF(valueOfTextField)
                                    viewModel.updateStateInputNetWork(false)
                                    onPDFOnLineScreen()
                                }
                            }) {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .fillMaxWidth(),
                                text = "Open file",
                                style = TextStyle(
                                    color = itemInComponentColor,
                                    fontWeight = W700,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonMain(modifier: Modifier, content: String, icon: Int) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = icon),
                tint = itemInComponentColor,
                contentDescription = null
            )

            Text(
                text = content,
                style = TextStyle(
                    color = itemInComponentColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
