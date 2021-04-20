package com.android.walmart.base

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseWalmartFragment: Fragment() {
    protected fun isAlive() = activity != null && activity?.isFinishing == false

    protected fun showMsg(msg: String) {
        if (isAlive().not()) return
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}