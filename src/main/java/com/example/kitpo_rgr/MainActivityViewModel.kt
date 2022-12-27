package com.example.kitpo_rgr

import androidx.lifecycle.ViewModel
import com.example.kitpo_rgr.Builder.Builder
import com.example.kitpo_rgr.List.TList

class MainActivityViewModel : ViewModel() {
    var builder: Builder = Factory.getBuilderByName("Integer")
    var list: TList = TList(builder)
    var logText: String = ""
}