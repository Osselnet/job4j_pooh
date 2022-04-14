package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String httpRequestType;
        String poohMode;
        String sourceName;
        String param;
        String[] lines = content.split(System.lineSeparator());
        String[] reqParam = content.split(" ");
        httpRequestType = reqParam[0];
        String[] modeAndName = reqParam[1].split("/");
        poohMode = modeAndName[1];
        sourceName = modeAndName[2];
        param = lines[lines.length - 1];
        if ("GET".equals(httpRequestType)) {
            if (modeAndName.length < 4) {
                param = "";
            } else {
                param = modeAndName[3];
            }
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
 }
