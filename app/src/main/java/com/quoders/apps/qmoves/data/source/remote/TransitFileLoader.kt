package com.quoders.apps.qmoves.data.source.remote

import com.quoders.apps.qmoves.data.Line
import com.quoders.apps.qmoves.data.Result
import com.quoders.apps.qmoves.tools.ZipHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type


/**
 * Loader class that takes a file with transit data and
 * returns the content as model entities.
 */
object TransitFileLoader {

    fun loadContentFile (filePath: String): Result<List<Line>> {
        val content = ZipHelper.extractFileContentInMemory(filePath)
            ?: return Result.Error(Exception("Unable to read ${filePath}"))

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            Line::class.java
        )
        val adapter: JsonAdapter<List<Line>> = moshi.adapter(listMyData)
        try {
            val lines = adapter.fromJson(content)
            return Result.Success(lines!!)
        }
        catch (e: Exception) {
            return Result.Error(e)
        }
    }
}