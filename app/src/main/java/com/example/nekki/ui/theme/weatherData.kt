data class Root(
    val location: Location,
    val current: Current,
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @JsonProperty("tz_id")
    val tzId: String,
    @JsonProperty("localtime_epoch")
    val localtimeEpoch: Long,
    val localtime: String,
)

annotation class JsonProperty(val value: String)

data class Current(
    @JsonProperty("last_updated_epoch")
    val lastUpdatedEpoch: Long,
    @JsonProperty("last_updated")
    val `last_updated`: String,
    val `temp_c`: Double,
    @JsonProperty("temp_f")
    val `temp_f`: Double,
    @JsonProperty("is_day")
    val `is_Day`: Long,
    val condition: Condition,
    @JsonProperty("wind_mph")
    val `vis_miles`: Double,
    @JsonProperty("wind_kph")
    val `wind_kph` : Double,
    @JsonProperty("wind_degree")
    val `wind_degree`: Long,
    @JsonProperty("wind_dir")
    val `wind_dir` : String,
    @JsonProperty("pressure_mb")
    val `pressure_mb`: Double,
    @JsonProperty("pressure_in")
    val `pressure_in`: Double,
    @JsonProperty("precip_mm")
    val `precip_mm`: Double,
    @JsonProperty("precip_in")
    val `precip_in`: Double,
    val humidity: Long,
    val cloud: Long,
    @JsonProperty("feelslike_c")
    val `feelslike_c`: Double,
    @JsonProperty("feelslike_f")
    val `feelslike_f`: Double,
    @JsonProperty("vis_km")
    val `vis_km`: Double,
    val uv: Double,
    @JsonProperty("gust_mph")
    val `gust_mph`: Double,
    @JsonProperty("gust_kph")
    val `gust_kph`: Double,
    @JsonProperty("air_quality")
    val `air_quality`: AirQuality,
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Int,
)

data class AirQuality(
    val co: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    @JsonProperty("pm2_5")
    val `pm2_5`: Double,
    val pm10: Double,
    @JsonProperty("us-epa-index")
    val `us-epa-index`: Long,
    @JsonProperty("gb-defra-index")
    val `gb-defra-index`: Long,
)


