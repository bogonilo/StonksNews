package com.lorenzo.stonksnews.model.response.yfapi

import androidx.lifecycle.LiveData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class BaseYFApiResponse<T: YFApiResponse>(
    open val finance: FinanceResponseWrapper<T>
) {
    @JsonClass(generateAdapter = true)
    data class FinanceResponseWrapper<T>(
        val error: String?,
        val result: List<T>
    )
}
