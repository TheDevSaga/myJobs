import com.example.myjobs.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>
): Resource<T> {
    return withContext(dispatcher) {
        try {
            val response = apiCall.invoke()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(convertErrorBody(response.errorBody())?: response.message())
            }
        } catch (e: java.lang.Exception) {
            Resource.Error(e.message)
        }
    }
}

private fun convertErrorBody(errorBody: okhttp3.ResponseBody?): String? {
    return try {
        val obj = JSONObject(errorBody!!.string())
        obj["message"].toString()
    } catch (exception: Exception) {
        null
    }
}