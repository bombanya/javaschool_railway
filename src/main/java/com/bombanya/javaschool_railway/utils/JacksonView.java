package com.bombanya.javaschool_railway.utils;

public class JacksonView {

    public interface MinimalInfo {}

    public interface UserInfo extends MinimalInfo {}

    public interface TrainFullInfo extends UserInfo {}

    public interface RouteFullInfo extends UserInfo {}
}
