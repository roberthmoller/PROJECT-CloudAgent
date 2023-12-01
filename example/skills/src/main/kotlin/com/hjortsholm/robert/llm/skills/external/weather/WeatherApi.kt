package com.hjortsholm.robert.llm.skills.external.weather

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "Weather", url = "https://api.open-meteo.com/v1")
interface WeatherApi {
    @GetMapping("/forecast?timezone=auto&latitude={latitude}&longitude={longitude}&hourly=temperature_2m&daily=weathercode,sunrise,sunset&current_weather=true&forecast_days=1")
    fun getWeather(@PathVariable latitude: String, @PathVariable longitude: String): String
}