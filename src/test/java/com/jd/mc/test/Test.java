package com.jd.mc.test;

import com.alibaba.fastjson.JSON;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.uwetrottmann.tmdb2.services.SearchService;
import okhttp3.Response;
import retrofit2.Call;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 田继东 on 2022/6/14 15:18
 */
public class Test {
    private static final Properties movieProperties = new Properties();

    private static String[] movieNames = null;
    private static String[] typeIgnoreArray = null;

    private static Tmdb tmdb = new Tmdb("bebcdef9de7e0135fbb0a666eaae3aec");
    private static SearchService searchService = tmdb.searchService();

    public static void main(String[] args) {

        //1，加载配置文件
        loadConfig();

        //2，加载电影文件
        List<String> movieNameList = loadMovie();

        //3，获取电影明细信息
        for (String curMovieName : movieNameList) {
            getMovieInfoFramTMDB(curMovieName);
        }


    }

    private static void loadConfig() {
        InputStream in = Test.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            movieProperties.load(in);
        } catch (Throwable e) {
            throw new RuntimeException("配置文件加载失败", e);
        }
        movieNames = movieProperties.getProperty("name").split(",");
        typeIgnoreArray = movieProperties.getProperty("typeIgnore").split(",");
    }

    private static List<String> loadMovie() {
        File movieFile = new File("\\\\192.168.2.105\\13011892042_Home1\\01-视频\\01-电影");

        File[] listFiles = movieFile.listFiles();
        if (listFiles == null) {
            throw new RuntimeException("指定目录下无文件");
        }
        List<String> movieList = new ArrayList<>();
        for (File curFile : listFiles) {

            if (curFile.isDirectory()) {
                continue;
            }

            String curFileName = curFile.getName();
            String curFileType = curFileName.substring(curFileName.lastIndexOf(".") + 1);

            if (checkType(curFileType)) {
                continue;
            }

            String curFileNewName = curFile.getName();
            for (String curMovileName : movieNames) {
                curFileNewName = curFileNewName.replace(curMovileName, "");
            }
            curFileNewName = curFileNewName.replaceAll("\\.", "");
            curFileNewName = curFileNewName.substring(0, curFileNewName.lastIndexOf(curFileType));

            System.out.println(curFileNewName + "--读取成功");
            movieList.add(curFileNewName);
        }

        if (movieList.size() == 0) {
            throw new RuntimeException("指定目录下无电影文件");
        }

        return movieList;
    }


    private static boolean checkType(String type) {
        for (String curType : typeIgnoreArray) {
            if (type.equals(curType)) {
                return true;
            }
        }
        return false;
    }

    private static void getMovieInfoFramTMDB(String movieName) {

        Call<MovieResultsPage> call = searchService.movie(movieName, null, "zh", "zh", true, null, null);
        try {
            MovieResultsPage movieResults = call.execute().body();
            if(movieResults == null || movieResults.results == null || movieResults.results.size() == 0){
                System.out.println("很抱歉，未获取到关于【"+movieName+"】的相关电影信息");
            }
            for (BaseMovie result : movieResults.results) {
                MovieInfo curMovieInfo = new MovieInfo(movieName,result);
                System.out.println(curMovieInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
