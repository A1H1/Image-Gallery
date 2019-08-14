package in.devco.imagegallery.View;

import java.util.List;

import in.devco.imagegallery.Model.Photo;

public interface IPhotoListView {
    void update(List<Photo> photos);
    void updateFailed();
}
