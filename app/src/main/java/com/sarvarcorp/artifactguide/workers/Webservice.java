package com.sarvarcorp.artifactguide.workers;

import com.sarvarcorp.artifactguide.entities.Guide;
import com.sarvarcorp.artifactguide.entities.GuideType;
import com.sarvarcorp.artifactguide.entities.UniversalItem;
import com.sarvarcorp.artifactguide.repositories.ResponseAdapter;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.Query;

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
    @GET("/api/universal_items/parent/{parentId}")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<List<UniversalItem>>> getUniversalItems(
            @Header("token") String token,
            @Path("parentId") int parentId,
            @Query("updatesOnly") int updatesOnly
    );

    @GET("/api/universal_items/{id}")
    @Headers({
            "Authorization: {token}",
            "X-Requested-With: XMLHttpRequest",
            "Content-Type: application/json"
    })
    Call<ResponseAdapter<UniversalItem>> getUniversalItem(
            @Header("token") String token,
            @Path("id") int id,
            @Query("updatesOnly") int updatesOnly
    );
}
