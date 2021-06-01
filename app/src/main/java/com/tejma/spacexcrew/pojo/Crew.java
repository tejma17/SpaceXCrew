package com.tejma.spacexcrew.pojo;



import androidx.room.Entity;

import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "crew_table", indices = @Index(value = {"id"}, unique = true))
public class Crew implements Serializable
{

    @PrimaryKey(autoGenerate = true)
    private int tableId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("agency")
    @Expose
    private String agency;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("wikipedia")
    @Expose
    private String wikipedia;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * No args constructor for use in serialization
     *
     */
    public Crew() {
    }

    /**
     *
     * @param image
     * @param agency
     * @param name
     * @param wikipedia
     * @param id
     * @param status
     */
    public Crew(String name, String agency, String image, String wikipedia, String status, String id) {
        super();
        this.name = name;
        this.agency = agency;
        this.image = image;
        this.wikipedia = wikipedia;
        this.status = status;
        this.id = id;
    }



    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}