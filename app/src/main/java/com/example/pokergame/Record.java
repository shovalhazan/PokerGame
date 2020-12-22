package com.example.pokergame;

import com.google.android.gms.maps.model.LatLng;

public class Record implements Comparable<Record>{

        private String name;
        private String date;
        private int score  ;
        private LatLng location= new LatLng(0,0);
        public Record() { }

        public Record(String name, String date, int score) {
            this.name = name;
            this.date = date;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public Record setName(String name) {
            this.name = name;
            return this;
        }

        public String getDate() {
            return date;
        }

        public Record setDate(String date) {
            this.date = date;
            return this;
        }

        public int getScore() {
            return score;
        }

        public Record setScore(int score) {
            this.score = score;
            return this;
        }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "name :'" + name +
                ", date='" + date +
                ", score=" + score ;
    }

    @Override
        public int compareTo(Record o) {
            return o.getScore()-this.getScore();
        }
    }


