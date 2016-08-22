package kr.edcan.lumihana.carhome.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kimok_000 on 2016-08-21.
 */
public class WeatherData {

    /**
     * message : 성공
     * code : 9200
     * requestUrl : /weather/current/minutely?village=도곡동&county=강남구&city=서울&version=1
     */
    @SerializedName("result")
    private ResultBean result;
    /**
     * alertYn : Y
     * stormYn : Y
     */

    @SerializedName("common")
    private CommonBean common;

    public WeatherData(){

    }

    public WeatherData(ResultBean result, CommonBean common, WeatherBean weather) {
        this.result = result;
        this.common = common;
        this.weather = weather;
    }

    @SerializedName("weather")
    private WeatherBean weather;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public CommonBean getCommon() {
        return common;
    }

    public void setCommon(CommonBean common) {
        this.common = common;
    }

    public WeatherBean getWeather() {
        return weather;
    }

    public void setWeather(WeatherBean weather) {
        this.weather = weather;
    }

    public static class ResultBean {
        @SerializedName("message")
        private String message;
        @SerializedName("code")
        private int code;
        @SerializedName("requestUrl")
        private String requestUrl;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getRequestUrl() {
            return requestUrl;
        }

        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }
    }

    public static class CommonBean {
        @SerializedName("alertYn")
        private String alertYn;
        @SerializedName("stormYn")
        private String stormYn;

        public CommonBean(String alertYn, String stormYn) {
            this.alertYn = alertYn;
            this.stormYn = stormYn;
        }

        public String getAlertYn() {
            return alertYn;
        }

        public void setAlertYn(String alertYn) {
            this.alertYn = alertYn;
        }

        public String getStormYn() {
            return stormYn;
        }

        public void setStormYn(String stormYn) {
            this.stormYn = stormYn;
        }
    }

    public static class WeatherBean {
        /**
         * station : {"name":"서초","id":"401","type":"KMA","latitude":"37.4889100000","longitude":"127.0156200000"}
         * wind : {"wdir":"0.00","wspd":"0.00"}
         * precipitation : {"type":"0","sinceOntime":"0.00"}
         * sky : {"name":"구름많음","code":"SKY_A03"}
         * rain : {"sinceOntime":"0.00","sinceMidnight":"0.00","last10min":"0.00","last15min":"0.00","last30min":"0.00","last1hour":"0.00","last6hour":"0.00","last12hour":"0.00","last24hour":"0.00"}
         * temperature : {"tc":"30.10","tmax":"34.30","tmin":"26.10"}
         * humidity :
         * pressure : {"surface":"","seaLevel":""}
         * lightning : 0
         * timeObservation : 2016-08-21 23:50:00
         */

        @SerializedName("minutely")
        private List<MinutelyBean> minutely;

        public List<MinutelyBean> getMinutely() {
            return minutely;
        }

        public WeatherBean(List<MinutelyBean> minutely) {
            this.minutely = minutely;
        }

        public void setMinutely(List<MinutelyBean> minutely) {
            this.minutely = minutely;
        }

        public static class MinutelyBean {
            public MinutelyBean(StationBean station, WindBean wind, PrecipitationBean precipitation, SkyBean sky, RainBean rain, TemperatureBean temperature, String humidity, PressureBean pressure, String lightning, String timeObservation) {
                this.station = station;
                this.wind = wind;
                this.precipitation = precipitation;
                this.sky = sky;
                this.rain = rain;
                this.temperature = temperature;
                this.humidity = humidity;
                this.pressure = pressure;
                this.lightning = lightning;
                this.timeObservation = timeObservation;
            }

            /**
             * name : 서초
             * id : 401
             * type : KMA
             * latitude : 37.4889100000
             * longitude : 127.0156200000
             */



            @SerializedName("station")
            private StationBean station;
            /**
             * wdir : 0.00
             * wspd : 0.00
             */
            @SerializedName("wind")
            private WindBean wind;
            /**
             * type : 0
             * sinceOntime : 0.00
             */
            @SerializedName("precipitation")
            private PrecipitationBean precipitation;
            /**
             * name : 구름많음
             * code : SKY_A03
             */
            @SerializedName("sky")
            private SkyBean sky;
            /**
             * sinceOntime : 0.00
             * sinceMidnight : 0.00
             * last10min : 0.00
             * last15min : 0.00
             * last30min : 0.00
             * last1hour : 0.00
             * last6hour : 0.00
             * last12hour : 0.00
             * last24hour : 0.00
             */
            @SerializedName("rain")
            private RainBean rain;
            /**
             * tc : 30.10
             * tmax : 34.30
             * tmin : 26.10
             */
            @SerializedName("temperature")
            private TemperatureBean temperature;
            @SerializedName("humidity")
            private String humidity;
            /**
             * surface :
             * seaLevel :
             */
            @SerializedName("pressure")
            private PressureBean pressure;
            @SerializedName("lightning")
            private String lightning;
            @SerializedName("timeObservation")
            private String timeObservation;

            public StationBean getStation() {
                return station;
            }

            public void setStation(StationBean station) {
                this.station = station;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public PrecipitationBean getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(PrecipitationBean precipitation) {
                this.precipitation = precipitation;
            }

            public SkyBean getSky() {
                return sky;
            }

            public void setSky(SkyBean sky) {
                this.sky = sky;
            }

            public RainBean getRain() {
                return rain;
            }

            public void setRain(RainBean rain) {
                this.rain = rain;
            }

            public TemperatureBean getTemperature() {
                return temperature;
            }

            public void setTemperature(TemperatureBean temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public PressureBean getPressure() {
                return pressure;
            }

            public void setPressure(PressureBean pressure) {
                this.pressure = pressure;
            }

            public String getLightning() {
                return lightning;
            }

            public void setLightning(String lightning) {
                this.lightning = lightning;
            }

            public String getTimeObservation() {
                return timeObservation;
            }

            public void setTimeObservation(String timeObservation) {
                this.timeObservation = timeObservation;
            }

            public static class StationBean {
                @SerializedName("name")
                private String name;
                @SerializedName("id")
                private String id;
                @SerializedName("type")
                private String type;
                @SerializedName("latitude")
                private String latitude;
                @SerializedName("longitude")
                private String longitude;

                public StationBean(String name, String id, String type, String latitude, String longitude) {
                    this.name = name;
                    this.id = id;
                    this.type = type;
                    this.latitude = latitude;
                    this.longitude = longitude;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getLatitude() {
                    return latitude;
                }

                public void setLatitude(String latitude) {
                    this.latitude = latitude;
                }

                public String getLongitude() {
                    return longitude;
                }

                public void setLongitude(String longitude) {
                    this.longitude = longitude;
                }
            }

            public static class WindBean {
                @SerializedName("wdir")
                private String wdir;
                @SerializedName("wspd")
                private String wspd;

                public WindBean(String wdir, String wspd) {
                    this.wdir = wdir;
                    this.wspd = wspd;
                }

                public String getWdir() {
                    return wdir;
                }

                public void setWdir(String wdir) {
                    this.wdir = wdir;
                }

                public String getWspd() {
                    return wspd;
                }

                public void setWspd(String wspd) {
                    this.wspd = wspd;
                }
            }

            public static class PrecipitationBean {
                @SerializedName("type")
                private String type;
                @SerializedName("sinceOntime")
                private String sinceOntime;

                public PrecipitationBean(String type, String sinceOntime) {
                    this.type = type;
                    this.sinceOntime = sinceOntime;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getSinceOntime() {
                    return sinceOntime;
                }

                public void setSinceOntime(String sinceOntime) {
                    this.sinceOntime = sinceOntime;
                }
            }

            public static class SkyBean {
                @SerializedName("name")
                private String name;
                @SerializedName("code")
                private String code;

                public SkyBean(String name, String code) {
                    this.name = name;
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
            }

            public static class RainBean {
                @SerializedName("sinceOntime")
                private String sinceOntime;
                @SerializedName("sinceMidnight")
                private String sinceMidnight;
                @SerializedName("last10min")
                private String last10min;
                @SerializedName("last15min")
                private String last15min;
                @SerializedName("last30min")
                private String last30min;
                @SerializedName("last1hour")
                private String last1hour;
                @SerializedName("last6hour")
                private String last6hour;
                @SerializedName("last12hour")
                private String last12hour;
                @SerializedName("last24hour")
                private String last24hour;

                public RainBean(String sinceOntime, String sinceMidnight, String last10min, String last15min, String last30min, String last1hour, String last6hour, String last12hour, String last24hour) {
                    this.sinceOntime = sinceOntime;
                    this.sinceMidnight = sinceMidnight;
                    this.last10min = last10min;
                    this.last15min = last15min;
                    this.last30min = last30min;
                    this.last1hour = last1hour;
                    this.last6hour = last6hour;
                    this.last12hour = last12hour;
                    this.last24hour = last24hour;
                }

                public String getSinceOntime() {
                    return sinceOntime;
                }

                public void setSinceOntime(String sinceOntime) {
                    this.sinceOntime = sinceOntime;
                }

                public String getSinceMidnight() {
                    return sinceMidnight;
                }

                public void setSinceMidnight(String sinceMidnight) {
                    this.sinceMidnight = sinceMidnight;
                }

                public String getLast10min() {
                    return last10min;
                }

                public void setLast10min(String last10min) {
                    this.last10min = last10min;
                }

                public String getLast15min() {
                    return last15min;
                }

                public void setLast15min(String last15min) {
                    this.last15min = last15min;
                }

                public String getLast30min() {
                    return last30min;
                }

                public void setLast30min(String last30min) {
                    this.last30min = last30min;
                }

                public String getLast1hour() {
                    return last1hour;
                }

                public void setLast1hour(String last1hour) {
                    this.last1hour = last1hour;
                }

                public String getLast6hour() {
                    return last6hour;
                }

                public void setLast6hour(String last6hour) {
                    this.last6hour = last6hour;
                }

                public String getLast12hour() {
                    return last12hour;
                }

                public void setLast12hour(String last12hour) {
                    this.last12hour = last12hour;
                }

                public String getLast24hour() {
                    return last24hour;
                }

                public void setLast24hour(String last24hour) {
                    this.last24hour = last24hour;
                }
            }

            public static class TemperatureBean {
                @SerializedName("tc")
                private String tc;
                @SerializedName("tmax")
                private String tmax;
                @SerializedName("tmin")
                private String tmin;

                public TemperatureBean(String tc, String tmax, String tmin) {
                    this.tc = tc;
                    this.tmax = tmax;
                    this.tmin = tmin;
                }

                public String getTc() {
                    return tc;
                }

                public void setTc(String tc) {
                    this.tc = tc;
                }

                public String getTmax() {
                    return tmax;
                }

                public void setTmax(String tmax) {
                    this.tmax = tmax;
                }

                public String getTmin() {
                    return tmin;
                }

                public void setTmin(String tmin) {
                    this.tmin = tmin;
                }
            }

            public static class PressureBean {
                @SerializedName("surface")
                private String surface;
                @SerializedName("seaLevel")
                private String seaLevel;

                public PressureBean(String surface, String seaLevel) {
                    this.surface = surface;
                    this.seaLevel = seaLevel;
                }

                public String getSurface() {
                    return surface;
                }

                public void setSurface(String surface) {
                    this.surface = surface;
                }

                public String getSeaLevel() {
                    return seaLevel;
                }

                public void setSeaLevel(String seaLevel) {
                    this.seaLevel = seaLevel;
                }
            }
        }
    }
}
