package com.whenwhat.framework.property

import android.view.View
import android.webkit.WebView
import android.widget.CompoundButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.htmlEncode
import androidx.viewpager.widget.ViewPager
import com.whenwhat.framework.other.ObservableList
import com.whenwhat.framework.other.Subscription
import com.whenwhat.framework.recyclerview.RecyclerAdapter
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KProperty0

@Suppress("UNCHECKED_CAST")
fun <T> KProperty0<T>.tryBind(skipInitial: Boolean = false, receiver: (T) -> Unit): Subscription? {
    val owner = (this as CallableReference).boundReceiver
    val delegate = owner.javaClass.declaredFields.find { field ->
        name == field.name.takeWhile { it != '$' }
    }?.apply {
        isAccessible = true
    }?.get(owner) as PropertyDelegate<T>

    delegate.addOnValueChangeListener(receiver)
    if (!skipInitial)
        receiver(get())
    return object : Subscription {
        override fun unSubscribe() {
            delegate.removeOnValueChangeListener(receiver)
        }
    }
}

fun <T> KProperty0<List<T>>.tryBind(adapter: RecyclerAdapter<T>, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        adapter.setItems(it)
    }
}

fun <T> ObservableList<T>.tryBindAsSource(adapter: RecyclerAdapter<T>, skipInitial: Boolean = false): Subscription? {
    if (!skipInitial)
        adapter.setItems(this)
    return subscribeOnChange { _, newList ->
        adapter.setItems(newList)
    }
}

fun KProperty0<Any?>.tryBindAsText(textView: TextView, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        textView.text = it?.toString()
    }
}

fun KProperty0<Boolean>.tryBindAsVisible(view: View, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        view.visibility = if (it) View.VISIBLE else View.GONE
    }
}

fun KProperty0<Boolean>.tryBindAsInvisible(view: View, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        view.visibility = if (it) View.INVISIBLE else View.VISIBLE
    }
}

fun KProperty0<Float>.tryBindAsAlpha(view: View, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        view.alpha = it
    }
}

fun KProperty0<Boolean>.tryBindAsEnable(view: View, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        view.isEnabled = it
    }
}

fun KProperty0<Boolean>.tryBindAsClickable(view: View, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        view.isClickable = it
    }
}

fun KProperty0<Boolean>.tryBindAsChecked(compoundButton: CompoundButton, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        compoundButton.isChecked = it
    }
}

fun KProperty0<String>.tryBindAsData(webView: WebView, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        webView.loadData(it, "text/html", it.htmlEncode())
    }
}

fun KProperty0<String>.tryBindAsUrl(webView: WebView, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        webView.loadUrl(it)
    }
}

fun KProperty0<Int>.tryBindAsCurrentItem(viewPager: ViewPager, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        viewPager.currentItem = it
    }
}

fun KProperty0<Int>.tryBindAsProgress(progressBar: ProgressBar, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        progressBar.progress = it
    }
}

fun KProperty0<Int>.tryBindAsMax(progressBar: ProgressBar, skipInitial: Boolean = false): Subscription? {
    return tryBind(skipInitial = skipInitial) {
        progressBar.max = it
    }
}