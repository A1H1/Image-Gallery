package `in`.devco.imagegallery.fragment

import `in`.devco.imagegallery.R
import `in`.devco.imagegallery.adapter.ImageAdapter
import `in`.devco.imagegallery.model.Photo
import `in`.devco.imagegallery.presenter.ISearchPresenter
import `in`.devco.imagegallery.presenter.SearchPresenter
import `in`.devco.imagegallery.view.ISearchView
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView

class Search : Fragment(), SearchView.OnQueryTextListener, ISearchView {
    private var recyclerView: RecyclerView? = null
    private var textView: TextView? = null
    private var progressBar: ProgressBar? = null
    private var searchPresenter: ISearchPresenter? = null
    private var layoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

        layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.search_rv)
        recyclerView?.layoutManager = layoutManager

        searchPresenter = SearchPresenter(this)

        textView = view.findViewById(R.id.search_tv)
        progressBar = view.findViewById(R.id.search_pb)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)

        val searchView: SearchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        progressBar?.visibility = View.VISIBLE
        searchPresenter?.search(p0.toString())
        return false
    }

    override fun searchFailed() {
        recyclerView?.visibility = View.GONE
        progressBar?.visibility = View.GONE
        textView?.visibility = View.VISIBLE
    }

    override fun update(photos: List<Photo>) {
        recyclerView?.visibility = View.VISIBLE
        recyclerView?.adapter = ImageAdapter(context, photos)

        textView?.visibility = View.GONE
        progressBar?.visibility = View.GONE
    }
}