package com.company;

public class Variables {
    public static final int SECOND = 1000;
    public static final Float KILO = 1000F;
    public static final Float MEGA = KILO * 1000;
    public static final Float GIGA = MEGA * 1000;
    public static final Float TERA = GIGA * 1000;

    public static final String KEY_ETH = "0x722f9b9842510c1e54f0b067a65fa853418fd6a7";
    public static final String KEY_ETC = "0xfee06bbe5856218a2982b8f8d0f3dfd00fb77d50";
    public static final String KEY_BTC = "1ASw5tYtmg5gMX5BCihGzLqpGSikUb9EYv";
    public static final String KEY_EXP = "0x256a517570cab39f42103e3d610d5cfc676a47b9";
    public static final String KEY_MUSIC = "0xafce4c193140ddfba901a0e65aa03c7c4a1cdaa1";
    public static final String KEY_PIRL = ""; // TODO: add exchange that can support it
    public static final String KEY_UBQ = "0x04a5fd9a0c7c145cef3123ecc0ecf76c7a67b9aa";
    public static final String KEY_XMR = "463tWEBn5XZJSxLU6uLQnQ2iY9xuNcDbjLSjkn3XAXHCbLrTTErJrBWYgHJQyrCwkNgYvyV3z8zctJLPCZy24jvb3NiTcTJ.e3f97519dac74bab8edc255d5c85fb716c90e9955d9a4afe96a2059af921ec32";
    public static final String KEY_GRS = "FnU8WjNnnzh8VPNYak4LkHgJjYtGjTBEti";

    public static final String HOME_FOLDER = System.getProperty("user.home");
    public static final String LOCATION_MAIN_FOLDER = HOME_FOLDER + "/mining-optimisation";
    public static final String WORKER_NAME = "worker1";
    public static final long CONFIGURATION_UPDATE_RATE = 6 * SECOND;
    public static final int HASHRATE_REPORTING_RATE = SECOND;
    public static final int POST_REQUEST_TIMEOUT = 60 * SECOND;

    public static final String HARDCODED_EMAIL = "saturnin.13@hotmail.fr";
}
