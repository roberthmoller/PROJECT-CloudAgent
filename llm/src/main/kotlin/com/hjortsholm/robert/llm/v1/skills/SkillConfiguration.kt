package com.hjortsholm.robert.llm.v1.skills

import com.hjortsholm.robert.spec.v1.advice.AdviceApi
import com.hjortsholm.robert.spec.v1.images.UnsplashApi
import com.hjortsholm.robert.spec.v1.jokes.chucknorris.ChuckNorrisJokesApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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

