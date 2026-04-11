package com.gubo.syllabusapp.feature.schedule.ui

import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import java.time.ZoneId
import java.time.ZonedDateTime

fun time(hour: Int, minute: Int): ZonedDateTime {
    val budapest = ZoneId.of("Europe/Budapest")

    return ZonedDateTime
        .now(budapest)
        .withHour(hour)
        .withMinute(minute)
        .withSecond(0)
        .withNano(0)
}

val days = listOf("Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek")

val sampleSchedule = mapOf(
    0 to listOf(
        ClassSession(
            subject = "Gyógypedagógiai szociológia",
            instructor = "Dr. Czövek Andrea",
            location = "ÉK.Als.3",
            startTime = time(9, 0),
            endTime = time(10, 40)
        ),
        ClassSession(
            subject = "Egészségtan – elsősegélynyújtás",
            instructor = "Fürné Mosoni Anita",
            location = "ÉK.Als.8",
            startTime = time(10, 50),
            endTime = time(12, 30)
        ),
        ClassSession(
            subject = "Funkcionális anatómia II.",
            instructor = "Fürné Mosoni Anita",
            location = "B.FSZ.KIS EA",
            startTime = time(14, 30),
            endTime = time(16, 10)
        ),
    ),
    1 to listOf(
        ClassSession(
            subject = "Tipikus és atipikus fejlődés pszichológiája",
            instructor = "Dr. Dávid Mária",
            location = "C.I.119",
            startTime = time(9, 55),
            endTime = time(12, 30)
        ),
        ClassSession(
            subject = "Nevelés- és gyógypedagógia-történet",
            instructor = "Dr. Pukánszky Béla",
            location = "ÉK.Als.2",
            startTime = time(14, 30),
            endTime = time(17, 5)
        ),
    ),
    2 to listOf(
        ClassSession(
            subject = "Gyógypedagógiai fejlődéstan",
            instructor = "Dr. Fodó Eszter",
            location = "ÉK.Als.2",
            startTime = time(7, 0),
            endTime = time(8, 40)
        ),
        ClassSession(
            subject = "Várandósság, szülés-születés",
            instructor = "Fürné Mosoni Anita",
            location = "ÉK.Als.2",
            startTime = time(10, 50),
            endTime = time(12, 30)
        ),
        ClassSession(
            subject = "Szellőztető",
            instructor = "Sárosi Dániel",
            location = "D.II.316",
            startTime = time(16, 20),
            endTime = time(18, 0)
        ),
    ),
    3 to listOf(
        ClassSession(
            subject = "Személyiség- és szociálpszichológia",
            instructor = "Kovácsné Tesléry Beáta",
            location = "ÉK.Als.2",
            startTime = time(9, 55),
            endTime = time(12, 30)
        ),
        ClassSession(
            subject = "Kereszténypedagógia",
            instructor = "Dr. Juhász Attila",
            location = "ÉRSEKUDVAR 4",
            startTime = time(9, 0),
            endTime = time(10, 40)
        ),
        ClassSession(
            subject = "Művészeti nevelés módszertana",
            instructor = "Szabó Anita",
            location = "B.II.207",
            startTime = time(12, 40),
            endTime = time(15, 15)
        ),
        ClassSession(
            subject = "Testnevelés, sport",
            instructor = "Dr. Ládi Tünde",
            location = "ÉK.II.207",
            startTime = time(15, 25),
            endTime = time(18, 0)
        ),
    ),
    4 to emptyList()
)