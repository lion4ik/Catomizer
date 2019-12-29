package com.github.catomizer.data

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment

class DownloadHelper(private val downloadManager: DownloadManager) {

    private fun downloadFile(url: String) {
        val uri = Uri.parse(url)
        val request =
            DownloadManager.Request(uri)
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    uri.lastPathSegment
                )
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        downloadManager.enqueue(request)
    }

    fun downloadFiles(urls: List<String>) {
        for (url in urls) {
            downloadFile(url)
        }
    }
}