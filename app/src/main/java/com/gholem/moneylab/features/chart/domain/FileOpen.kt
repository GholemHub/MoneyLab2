package com.gholem.moneylab.features.chart.domain

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

object FileOpen {
    @Throws(IOException::class)
    fun openFile(context: Context, url: File) {
        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName.toString() + ".provider",
            url
        )
        val intent = Intent(Intent.ACTION_VIEW)
        if (url.toString().contains(".xlsx")) {
            intent.setDataAndType(uri, "application/vnd.ms-excel")
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent)
    }
}