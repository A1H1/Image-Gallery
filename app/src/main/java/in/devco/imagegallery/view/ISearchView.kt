package `in`.devco.imagegallery.view

import `in`.devco.imagegallery.model.Photo

interface ISearchView {
    fun searchFailed()
    fun update(photos: List<Photo>)
}