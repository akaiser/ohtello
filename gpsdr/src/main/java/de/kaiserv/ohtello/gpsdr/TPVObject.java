package de.kaiserv.ohtello.gpsdr;

// However, until the sensor achieves satellite lock, those fixes will be "mode 1" - no valid data (mode 2 is a 2D fix, mode 3 is a 3D fix)
// https://gpsd.gitlab.io/gpsd/client-howto.html
// https://gpsd.gitlab.io/gpsd/gpsd_json.html

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringReader;
import java.math.BigDecimal;

/**
 * {
 * "class":"TPV",
 * "device":"/dev/ttyAMA0",
 * "status":2,                          GPS status: %d, 2=DGPS fix, otherwise not present.
 * "mode":3,                            NMEA mode: %d, 0=no mode value yet seen, 1=no fix, 2=2D, 3=3D.
 * "time":"2020-02-19T13:31:38.000Z",   Time/date stamp in ISO8601 format, UTC. May have a fractional part of up to .001sec precision. May be absent if the mode is not 2 or 3.
 * "ept":0.005,                         Estimated timestamp error in seconds. Present if time is present.
 * "lat":48.628132333,                  Latitude in degrees: +/- signifies North/South. Present when the mode is 2 or 3.
 * "lon":10.176790000,                  Longitude in degrees: +/- signifies East/West. Present when mode is 2 or 3.
 * "alt":497.800,                       Altitude, height above allipsoid, in meters
 * "epx":1.548,                         Longitude error estimate in meters. Present if the mode is 2D or 3D and DOPs can be calculated from the satellite view.
 * "epy":1.803,                         Latitude error estimate in meters. Present if the mode is 2 or 3 and DOPs can be calculated from the satellite view.
 * "epv":6.153,                         Estimated vertical error in meters. Present if the mode is 3 and DOPs can be calculated from the satellite view.
 * "eps":3.61                           Estimated speed error in meters per second. Present for consecutive 2D or 3D fixes.
 * "track":0.0000,                      Course over ground, degrees from true north.
 * "speed":0.016,                       Speed over ground, meters per second.
 * "climb":0.000,                       Climb (positive) or sink (negative) rate, meters per second.
 * }
 */

public class TPVObject {

    final int status;
    final int mode;
    final String time;
    final BigDecimal lat;
    final BigDecimal lon;

    public TPVObject(int status, int mode, String time, BigDecimal lat, BigDecimal lon) {
        this.status = status;
        this.mode = mode;
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }

    public static TPVObject fromJson(String json) {
        JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();

        // TODO: handle some NPEs here
        if (jsonObject == null) System.out.println("jsonObject is null");
        return new TPVObject(
                jsonObject.getInt("status"),
                jsonObject.getInt("mode"),
                jsonObject.getString("time"),
                jsonObject.getJsonNumber("lat").bigDecimalValue(),
                jsonObject.getJsonNumber("lon").bigDecimalValue()
        );
    }

    @Override
    public String toString() {
        return "TPVObject{" +
                "status=" + status +
                ", mode=" + mode +
                ", time='" + time + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
