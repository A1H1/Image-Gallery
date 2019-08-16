package in.devco.imagegallery.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import in.devco.imagegallery.R;
import in.devco.imagegallery.adapter.ImageAdapter;
import in.devco.imagegallery.model.Photo;
import in.devco.imagegallery.presenter.IPhotoListPresenter;
import in.devco.imagegallery.presenter.PhotoListPresenter;
import in.devco.imagegallery.view.IPhotoListView;

public class HomePage extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        IPhotoListView {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private IPhotoListPresenter photoListPresenter;
    private TextView textView;
    private LinearLayoutManager layoutManager;
    private ImageAdapter imageAdapter;
    private List<Photo> photos;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_homepage, container, false);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.main_activity_rv);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ScrollListener());

        swipeRefreshLayout = view.findViewById(R.id.main_activity_srl);
        swipeRefreshLayout.setOnRefreshListener(this);

        photoListPresenter = new PhotoListPresenter(this);
        photoListPresenter.fetchData();

        textView = view.findViewById(R.id.main_activity_tv);
        progressBar = view.findViewById(R.id.main_activity_pb);

        return view;
    }

    @Override
    public void onRefresh() {
        photoListPresenter.fetchData();
        photoListPresenter.reset();
    }

    @Override
    public void update(List<Photo> photos) {
        this.photos = photos;
        imageAdapter = new ImageAdapter(getContext(), this.photos);
        recyclerView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        recyclerView.setAdapter(imageAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateFailed() {
        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(List<Photo> photos) {
        progressBar.setVisibility(View.GONE);
        this.photos.addAll(photos);
        imageAdapter.notifyDataSetChanged();
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        private boolean isScrolling = false;

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (isScrolling && (layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition() == layoutManager.getItemCount())) {
                progressBar.setVisibility(View.VISIBLE);
                photoListPresenter.loadMoreData();
                isScrolling = false;
            }
        }
    }
}
