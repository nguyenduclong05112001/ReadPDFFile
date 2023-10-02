package com.longhrk.app.ui.screen

import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import com.longhrk.app.R
import com.longhrk.app.ui.util.PDFFileUtil.retrievePDFFromUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

@Composable
fun HomeScreen(
    onNextScreen: () -> Unit,
    onBackPress: () -> Unit
) {
    val notifyBackOutApp = stringResource(id = R.string.notify_back_out_app)
    val context = LocalContext.current
    var backPressTime = 0L
    val scope = rememberCoroutineScope()

    val fileName = "https://eximbank.com.vn/vn/download/HDSD_EximbankSmartOTP_EN.pdf"

    BackHandler {

        //handle when user back to out app
        if (backPressTime + 1500 > System.currentTimeMillis()) {
            onBackPress()
        } else {
            Toast.makeText(context, notifyBackOutApp, Toast.LENGTH_LONG).show()
        }
        backPressTime = System.currentTimeMillis()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { factoryContext ->
                PDFView(factoryContext, null).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                    )
                    scope.launch {
                        val fileStream: InputStream? = retrievePDFFromUrl(fileName)
                        withContext(Dispatchers.Main) {
                            fromStream(fileStream).load()
                        }
                    }
                }
            })
    }
}
