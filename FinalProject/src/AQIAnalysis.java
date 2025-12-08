public class AQIAnalysis {

    public static String getStatus(double aqiValue){
         if (aqiValue < 0)
            return "Error: Negative AQI";
    if (aqiValue<=50) return "Good";
    else if (aqiValue<=100) return "Moderate";
    else if (aqiValue<=150) return "Unhealthy for Sensitive People";
    else if (aqiValue<=200) return "Unhealthy";
    else if (aqiValue<=300) return "Very Unhealthy";
    else return "Hazardous";
    }

    public static boolean isHarmful(double aqiValue){
        return aqiValue > 100;
    }




}




