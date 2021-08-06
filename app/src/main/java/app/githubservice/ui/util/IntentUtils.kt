package app.githubservice.ui.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast


fun openUrlLink(context: Context, url: String) {
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        Toast.makeText(
            context, "No application can handle this request.", Toast.LENGTH_LONG
        ).show()
    }
}
