package in.devco.imagegallery.Model;

import java.util.List;

public class Photos {
    private int page;
    private int pages;
    private int perpage;
    private List<Photo> photo;

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public List<Photo> getPhoto() {
        return photo;
    }
}
