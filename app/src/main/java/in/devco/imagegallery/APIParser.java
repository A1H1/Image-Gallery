package in.devco.imagegallery;

import in.devco.imagegallery.objects.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIParser {

    @GET("rest/")
    Call<Result> getImages(
            @Query("method") String method,
            @Query("per_page") int perPage,
            @Query("page") int page,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") int nojsoncallback,
            @Query("extras") String extras
    );
}
