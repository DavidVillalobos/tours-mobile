package com.example.android.tours_mobile.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notification Fragment"
    }
    val text: LiveData<String> = _text
}