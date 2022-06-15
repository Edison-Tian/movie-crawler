package com.jd.mc.test;

import com.uwetrottmann.tmdb2.entities.MovieResultsPage;

/**
 * @author 田继东 on 2022/6/14 22:27
 */
public class MovieInfo {
    private MovieResultsPage movieResultsPage;

    private String name;

    public MovieInfo(MovieResultsPage movieResultsPage, String name) {
        this.movieResultsPage = movieResultsPage;
        this.name = name;
    }

    public MovieResultsPage getMovieResultsPage() {
        return movieResultsPage;
    }

    public void setMovieResultsPage(MovieResultsPage movieResultsPage) {
        this.movieResultsPage = movieResultsPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
