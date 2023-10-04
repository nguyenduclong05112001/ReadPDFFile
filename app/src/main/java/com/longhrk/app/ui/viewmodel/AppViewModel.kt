package com.longhrk.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    private var _inputStreamPDF = MutableStateFlow<InputStream?>(null)
    val inputStreamPDF = _inputStreamPDF

    private var _linkPDFFile = MutableStateFlow("")
    val linkPDFFile = _linkPDFFile.asStateFlow()

    private var _showInputNetWorkLink = MutableStateFlow(false)
    val showInputNetWorkLink = _showInputNetWorkLink.asStateFlow()

    fun updateStateInputNetWork(state: Boolean) {
        _showInputNetWorkLink.value = state
    }

    fun updateLinkFilePDF(url: String) {
        _linkPDFFile.value = url
    }

    fun updateInputStreamPDF(inputStream: InputStream) {
        _inputStreamPDF.value = inputStream
    }
}