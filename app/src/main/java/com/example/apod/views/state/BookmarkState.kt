package com.example.apod.views.state


sealed class BookmarkState {
    object Saved : BookmarkState()
    object Unsaved : BookmarkState()
}