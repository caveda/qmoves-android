package com.quoders.apps.qmoves.tools

import timber.log.Timber
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipInputStream

/**
 * Helper for zip operations
 */
object ZipHelper {

    /**
     * Extracts the content of the zip file in filePath in memory
     * and returns it as string.
     * Returns null if anything goes wrong.
     */
    fun extractFileContentInMemory(filePath: String): String? {
        var stream : InputStream? = null
        val sb  = StringBuilder()
        try {
            stream = FileInputStream(filePath)
            val zis = ZipInputStream(BufferedInputStream(stream))
            val buffer = ByteArray(1024)
            if (zis.nextEntry != null) {
                var count = -1
                while (zis.read(buffer).also { count = it } != -1) {
                    sb.append(String(buffer, 0, count))
                }
                zis.closeEntry()
            }
            zis.close()
        } catch (e: IOException) {
            Timber.e( "ExtractFileContentInMemory: Exception ${e.message}")
            return null
        } finally {
            stream?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    Timber.e( "ExtractFileContentInMemory: Exception closing stream ${e.message}")
                }
            }
        }
        return if (sb.isNotEmpty()) sb.toString() else null
    }
}