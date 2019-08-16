package in.devco.imagegallery.view;

import java.util.List;

import in.devco.imagegallery.model.Photo;

public interface IPhotoListView {
    void update(List<Photo> photos);
    void updateFailed();
    void loadMore(List<Photo> photos);
}
