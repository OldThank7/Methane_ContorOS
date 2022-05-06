package newland.com.methane;

public class SettingUtils {

    private static String ip;
    private static Integer port;
    private static Integer projectID,GateWayID,LoRaGateWayID,methaneMax;
    private static String fen,methane;
    private static String AccessToken;

    public SettingUtils() {
    }

    public SettingUtils(String ip, Integer port, Integer projectID, Integer gateWayID, Integer loRaGateWayID, String fen, String methane, Integer methaneMax) {
        this.ip = ip;
        this.port = port;
        this.projectID = projectID;
        this.GateWayID = gateWayID;
        this.LoRaGateWayID = loRaGateWayID;
        this.fen = fen;
        this.methane = methane;
        this.methaneMax = methaneMax;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        SettingUtils.ip = ip;
    }

    public static Integer getPort() {
        return port;
    }

    public static void setPort(Integer port) {
        SettingUtils.port = port;
    }

    public static Integer getProjectID() {
        return projectID;
    }

    public static void setProjectID(Integer projectID) {
        SettingUtils.projectID = projectID;
    }

    public static Integer getGateWayID() {
        return GateWayID;
    }

    public static void setGateWayID(Integer gateWayID) {
        GateWayID = gateWayID;
    }

    public static Integer getLoRaGateWayID() {
        return LoRaGateWayID;
    }

    public static void setLoRaGateWayID(Integer loRaGateWayID) {
        LoRaGateWayID = loRaGateWayID;
    }

    public static String getFen() {
        return fen;
    }

    public static void setFen(String fen) {
        SettingUtils.fen = fen;
    }

    public static String getMethane() {
        return methane;
    }

    public static void setMethane(String methane) {
        SettingUtils.methane = methane;
    }

    public static Integer getMethaneMax() {
        return methaneMax;
    }

    public static void setMethaneMax(Integer methaneMax) {
        SettingUtils.methaneMax = methaneMax;
    }

    public static String getAccessToken() {
        return AccessToken;
    }

    public static void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }
}
