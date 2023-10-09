package com.longhrk.app.ui.screen

import android.graphics.Color
import android.net.Uri
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import com.longhrk.app.ui.theme.backgroundColor
import com.longhrk.app.ui.theme.componentColor
import com.longhrk.app.ui.theme.itemInComponentColor
import com.longhrk.app.ui.theme.textColor
import com.longhrk.app.ui.viewmodel.AppViewModel
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun PDFOffLineScreen(
    onBackPress: () -> Unit,
    viewModel: AppViewModel
) {
    val scope = rememberCoroutineScope()
    val view = LocalView.current
    val uriPDF by viewModel.uriPDF.collectAsState()

    var showLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit, block = {
        if (uriPDF == Uri.EMPTY) {
            showLoading = true
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        if (showLoading) {
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
                        .background(backgroundColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp, horizontal = 10.dp),
                        text = "Error!",
                        style = TextStyle(
                            fontWeight = FontWeight.W700,
                            fontSize = 24.sp,
                            color = textColor,
                            textAlign = TextAlign.Start
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .clip(shape = RoundedCornerShape(10))
                            .border(
                                color = textColor,
                                width = 1.dp,
                                shape = RoundedCornerShape(10)
                            )
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 15.dp)
                    ) {
                        Text(
                            text = "Please check your internet or the link you entered is having problems, please try again. Thank you very much.",
                            style = TextStyle(
                                fontWeight = FontWeight.W200,
                                fontSize = 16.sp,
                                color = textColor
                            )
                        )
                    }
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
                            onClick = onBackPress
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .fillMaxWidth(),
                                text = "Close",
                                style = TextStyle(
                                    color = itemInComponentColor,
                                    fontWeight = FontWeight.W700,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        } else {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { factoryContext ->
                    PDFView(factoryContext, null).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT,
                        )
                        scope.launch {
                            withContext(Dispatchers.Main) {
                                fromUri(uriPDF)
                                    .onError { showLoading = true }
                                    .load()
                            }
                        }
                    }
                }
            )
        }
    }
}