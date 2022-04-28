package com.seiko.lightnovel.data.model.enums

enum class ArticleLibrary {
    All, // 全部文库
    DengekiBunko, // 电击文库
    FujimiFantasiaBunko, // 富士见文库
    KadokawaBunko, // 角川文库
    EmuefuBunkoJei, // MF文库J
    FamitsuBunko, // Fami通文库
    GABunko, // GA文库
    HJBunko, // HJ文库
    Ichijinsha, // 一迅社
    Shueisha, // 集英社
    Shogakukan, // 小学馆
    Kodansha, // 讲谈社
    TeenageBunko, // 少女文库
    Other, // 其他文库
    Game, // 游戏剧本
    Unknown; // 未知

    companion object
}

fun ArticleLibrary.Companion.valueOfTitle(title: String?) = when (title) {
    "全部文库" -> ArticleLibrary.All
    "电击文库" -> ArticleLibrary.DengekiBunko
    "富士见文库" -> ArticleLibrary.FujimiFantasiaBunko
    "角川文库" -> ArticleLibrary.KadokawaBunko
    "MF文库J" -> ArticleLibrary.EmuefuBunkoJei
    "Fami通文库" -> ArticleLibrary.FamitsuBunko
    "GA文库" -> ArticleLibrary.GABunko
    "HJ文库" -> ArticleLibrary.HJBunko
    "一迅社" -> ArticleLibrary.Ichijinsha
    "集英社" -> ArticleLibrary.Shueisha
    "小学馆" -> ArticleLibrary.Shogakukan
    "讲谈社" -> ArticleLibrary.Kodansha
    "少女文库" -> ArticleLibrary.TeenageBunko
    "其他文库" -> ArticleLibrary.Other
    "游戏剧本" -> ArticleLibrary.Game
    else -> ArticleLibrary.Unknown
}
