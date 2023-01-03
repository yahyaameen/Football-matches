package com.example.footballmatches;

import java.util.Date;

public class Match {
    String city;
    Date date;
    String sdate;
    String teamA;
    String teamB;
    String teamAlower;
    String teamBlower;
    String key;  

    Match()
    {

    }

    public Match(String city, Date date, String teamA, String teamB) {
        this.city = city;
        this.date = date;
        this.teamA = teamA;
        this.teamB = teamB;
        this.teamAlower = teamA.toLowerCase();
        this.teamBlower = teamB.toLowerCase();
        int day = date.getDate();
        int month = date.getMonth() + 1;
        int year = date.getYear();
        sdate = day + "/" + month + "/" + year;
        key = null;
    }

    public String getTeamAlower() {
        return teamAlower;
    }

    public void setTeamAlower(String teamAlower) {
        this.teamAlower = teamAlower;
    }

    public String getTeamBlower() {
        return teamBlower;
    }

    public void setTeamBlower(String teamBlower) {
        this.teamBlower = teamBlower;
    }
// getters and setters

    public String getSdate() {
        return sdate;
    }

    public String getCity() {
        return city;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public  Date getDate(){
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }
}
