package in.devco.imagegallery;

import in.devco.imagegallery.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIParser {

    @GET("rest/")
    Call<Result> getImages(
            @Query(Config.API_METHOD_KEY) String method,
            @Query("per_page") int perPage,
            @Query("page") int page,
            @Query(Config.API_KEY) String apiKey,
            @Query(Config.API_FORMAT_KEY) String format,
            @Query(Config.API_NO_JSON_KEY) int nojsoncallback,
            @Query(Config.API_EXTRAS_KEY) String extras
    );

    @GET("rest/")
    Call<Result> searchItem(
            @Query(Config.API_METHOD_KEY) String method,
            @Query(Config.API_KEY) String apiKey,
            @Query(Config.API_FORMAT_KEY) String format,
            @Query(Config.API_NO_JSON_KEY) int nojsoncallback,
            @Query(Config.API_EXTRAS_KEY) String extras,
            @Query(Config.API_TEXT_KEY) String text
    );
}
