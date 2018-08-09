package pt.boldint.carbon.boldhunter.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar


/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun snackbar(view: View, message: Int) = Snackbar
        .make(view, message, Snackbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun longSnackbar(view: View, message: Int) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun snackbar(view: View, message: String) = Snackbar
        .make(view, message, Snackbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun longSnackbar(view: View, message: String) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .apply { show() }


/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun snackbar(view: View, message: Int, actionText: Int, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_SHORT)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun longSnackbar(view: View, message: Int, actionText: Int, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun snackbar(view: View, message: String, actionText: String, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_SHORT)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun longSnackbar(view: View, message: String, actionText: String, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .apply {
            setAction(actionText, action)
            show()
        }