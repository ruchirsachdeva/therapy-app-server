package com.lnu.foundation.service;

public class DistanceHelper {

    public static double distance(Double lat1, Double lon1, Double lat2, Double lon2, String unit) {
        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null || (lat1.equals(lat2)) && (lon1.equals(lon2))) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {   // kms
                dist = dist * 1.609344;
            } else if (unit == "N") {  // Nautical miles
                dist = dist * 0.8684;
            }
            return (dist);  // Miles
        }
    }
}
