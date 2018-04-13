package com.company;

public class Variables {
    public static final int SECOND = 1000;
    public static final Float KILO = 1000F;
    public static final Float MEGA = KILO * 1000;
    public static final Float GIGA = MEGA * 1000;
    public static final Float TERA = GIGA * 1000;

    public static final String KEY_ETHEREUM = "0x128BB7821371Ef65Dfc6A9A0153121B3B3218deb";
    public static final String KEY_MONERO = "49vaYQpbZv9c4Cuzu31mMxHSbiMweoc6SDnHKq4rL7MxJfe4Du89PNW4ZdbcvquTo1JWZ9E8BDQHteTAH14g4qCXBidkmzx";
    public static final String KEY_GROESTLCOIN = "FnU8WjNnnzh8VPNYak4LkHgJjYtGjTBEti";

    public static final String HOME_FOLDER = System.getProperty("user.home");
    public static final String LOCATION_MAIN_FOLDER = HOME_FOLDER + "/mining-optimisation";
    public static final String WORKER_NAME = "worker1";
    public static final long CONFIGURATION_UPDATE_RATE = 6 * SECOND;
    public static final int HASHRATE_REPORTING_RATE = SECOND;
    public static final int POST_REQUEST_TIMEOUT = 60 * SECOND;
}
