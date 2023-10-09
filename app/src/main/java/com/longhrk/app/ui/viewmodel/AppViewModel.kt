package com.longhrk.app.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    private var _uriPDF = MutableStateFlow<Uri>(Uri.EMPTY)
    val uriPDF = _uriPDF

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

    fun updateUriPDF(uri: Uri) {
        _uriPDF.value = uri
    }
}