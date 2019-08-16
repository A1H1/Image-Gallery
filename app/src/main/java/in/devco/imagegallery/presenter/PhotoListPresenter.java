package in.devco.imagegallery.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import in.devco.imagegallery.APIParser;
import in.devco.imagegallery.Config;
import in.devco.imagegallery.model.Photos;
import in.devco.imagegallery.model.Result;
import in.devco.imagegallery.view.IPhotoListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoListPresenter implements IPhotoListPresenter, Callback<Result> {
    private IPhotoListView iPhotoListView;
    private int page = 1;
    private boolean loadMore = false;

    public PhotoListPresenter(IPhotoListView iPhotoListView) {
        this.iPhotoListView = iPhotoListView;
    }

    @Override
    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIParser apiParser = retrofit.create(APIParser.class);
        Call<Result> call = apiParser.getImages(Config.API_METHOD_RECENT, 20, page, Config.API_KEY_VALUE, Config.API_FORMAT_VALUE, 1, Config.API_EXTRAS_VALUE);
        call.enqueue(this);
    }

    @Override
    public void loadMoreData() {
        loadMore = true;
        page++;
        fetchData();
    }

    @Override
    public void reset() {
        page = 1;
    }

    @Override
    public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
        if (!response.isSuccessful() || response.body() == null) {
            Log.e(Config.LOG_NETWORK, "Code: " + response.code());
            iPhotoListView.updateFailed();
        } else{
            Photos photos = response.body().getPhotos();
            if (loadMore) {
                iPhotoListView.loadMore(photos.getPhoto());
                loadMore = false;
            } else
                iPhotoListView.update(photos.getPhoto());
        }
    }

    @Override
    public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
        Log.e(Config.LOG_NETWORK, t.getMessage());
        iPhotoListView.updateFailed();
    }
}
