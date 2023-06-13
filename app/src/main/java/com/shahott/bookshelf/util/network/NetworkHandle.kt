package com.shahott.bookshelf.util.network

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@Keep
data class ErrorResponse(
    @field:Json(name = "message")
    val message: String? = "", // Invalid email, phone number or password.
    @field:Json(name = "errorCode")
    val errorCode: Int? = 0 // 1000
)

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) :
        ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        val code = throwable.code()
        ErrorResponse(
            message = getHttpErrorMessage(code),
            errorCode = code
        )
    }
}

fun getHttpErrorMessage(code: Int): String {
    val httpErrorType = when (code) {
        400 -> HttpErrorType.BadRequest("")
        401 -> HttpErrorType.NotAuthorized
        403 -> HttpErrorType.Forbidden
        404 -> HttpErrorType.NotFound
        422 -> HttpErrorType.DataInvalid("")
        500 -> HttpErrorType.InternalServerError
        502 -> HttpErrorType.BadGateway
        else -> HttpErrorType.Unknown
    }
    return httpErrorType.javaClass.name
}


@Keep
sealed class HttpErrorType {
    @Keep
    data class BadRequest(val errorSignUp: String? = null) : HttpErrorType()

    @Keep
    object NotAuthorized : HttpErrorType()

    @Keep
    object Forbidden : HttpErrorType()

    @Keep
    object NotFound : HttpErrorType()

    @Keep
    data class DataInvalid(val errorResponseModel: String) : HttpErrorType()

    @Keep
    object InternalServerError : HttpErrorType()

    @Keep
    object BadGateway : HttpErrorType()

    @Keep
    object Unknown : HttpErrorType()
}


