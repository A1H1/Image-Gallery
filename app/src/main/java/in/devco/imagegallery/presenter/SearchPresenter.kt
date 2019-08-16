package `in`.devco.imagegallery.presenter

import `in`.devco.imagegallery.APIParser
import `in`.devco.imagegallery.Config
import `in`.devco.imagegallery.model.Result
import `in`.devco.imagegallery.view.ISearchView
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchPresenter(private var searchView: ISearchView?) : ISearchPresenter, Callback<Result> {

    override fun search(text: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiParser = retrofit.create(APIParser::class.java)
        val call = apiParser.searchItem(Config.API_METHOD_SEARCH, Config.API_KEY_VALUE, Config.API_FORMAT_VALUE, 1, Config.API_EXTRAS_VALUE, text)
        call.enqueue(this)
    }

    override fun onFailure(call: Call<Result>, t: Throwable) {
        Log.e(Config.LOG_NETWORK, t.message)
        searchView?.searchFailed()
    }

    override fun onResponse(call: Call<Result>, response: Response<Result>) {
        if (!response.isSuccessful) {
            Log.e(Config.LOG_NETWORK, "Code: " + response.code())
            searchView?.searchFailed()
        } else {
            val photos = response.body()!!.photos
            searchView?.update(photos.photo)
        }
    }
}