package com.sarvarcorp.artifactguidedemo.workers;

import com.sarvarcorp.artifactguidedemo.entities.Guide;
import com.sarvarcorp.artifactguidedemo.entities.GuideType;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;
import com.sarvarcorp.artifactguidedemo.repositories.ResponseAdapter;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.Call;

public interface Webservice {
    @GET("/guide_type.json")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<List<GuideType>>> getGuideTypes(@Header("token") String token);

    @GET("/guide/{typeId}.json")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<List<Guide>>> getGuides(@Header("token") String token, @Path("typeId") int typeId);

    @GET("/guide/show/{id}.json")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<Guide>> getGuide(@Header("token") String token, @Path("id") int id);


    
    /*
     * Список универсальных элементов
     * @param token
     * @param parentId
     * @return
     */
    @GET("/api/universal_items/parent/{parentId}?updates_only={updatesOnly}")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<List<UniversalItem>>> getUniversalItems(
            @Header("token") String token,
            @Path("updatesOnly") boolean updatesOnly,
            @Path("parentId") int parentId
    );

    @GET("/api/universal_items/{id}?updates_only={updatesOnly}")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<UniversalItem>> getUniversalItem(
            @Header("token") String token,
            @Path("updatesOnly") boolean updatesOnly,
            @Path("id") int id
    );
}
