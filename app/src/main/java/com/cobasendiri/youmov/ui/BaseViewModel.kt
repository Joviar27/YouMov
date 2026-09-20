package com.cobasendiri.youmov.ui

import androidx.lifecycle.ViewModel
import com.cobasendiri.youmov.domain.Result

abstract class BaseViewModel: ViewModel() {

    protected fun <T> Result<T>.handleResult(
        onError: (() -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null
    ){
        when(this){
            is Result.Success -> onSuccess?.invoke(this.data)
            is Result.Error -> {
                onError?.invoke()
                showToast("Something went wrong, please try again")
            }
        }
    }

    open fun showToast(message: String){}
}