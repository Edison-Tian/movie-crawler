package com.jd.mc.test;

import com.alibaba.fastjson.JSON;
import com.uwetrottmann.tmdb2.entities.BaseMovie;

/**
 * @author 田继东 on 2022/6/14 22:27
 */
public class MovieInfo {
    private BaseMovie baseMovie;

    private String name;

    public MovieInfo(String name ,BaseMovie baseMovie) {
        this.name = name;
        this.baseMovie = baseMovie;
    }


    @Override
    public String toString() {
        return name + ":" + JSON.toJSONString(baseMovie);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseMovie getBaseMovie() {
        return baseMovie;
    }

    public void setBaseMovie(BaseMovie baseMovie) {
        this.baseMovie = baseMovie;
    }
}
