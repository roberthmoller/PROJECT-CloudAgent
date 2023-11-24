package com.hjortsholm.robert.llm.function.v1.skills

import com.hjortsholm.robert.llm.Skill
import com.hjortsholm.robert.spec.v1.external.advice.AdviceApi
import com.hjortsholm.robert.spec.v1.external.images.UnsplashApi
import com.hjortsholm.robert.spec.v1.external.jokes.chucknorris.ChuckNorrisJokesApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

interface AdviceClient: AdviceApi
interface ChuckNorrisJokesClient: ChuckNorrisJokesApi
interface UnsplashClient: UnsplashApi

@Configuration
class SkillConfiguration {
    @Bean
    fun all(
        advice: AdviceApi,
        chuckNorrisJokes: ChuckNorrisJokesApi,
        unsplash: UnsplashApi,
    ): Map<String, Skill> {
        return listOf(
            Skill(chuckNorrisJokes::getRandomJoke),
            Skill(advice::getRandomAdvice),
            Skill(advice::searchForAdvice),
            Skill(unsplash::getRandomPhoto),
            Skill(unsplash::getRandomPhotoWithSize),
            Skill(unsplash::getPhotoOfSubject),
            Skill(unsplash::getPhotoOfSubjectWithSize),
        ).associateBy { it.skill }
    }
}

