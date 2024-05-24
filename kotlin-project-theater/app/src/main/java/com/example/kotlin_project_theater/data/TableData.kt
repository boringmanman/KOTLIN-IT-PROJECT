package com.example.kotlin_project_theater.data

import com.example.kotlin_project_theater.R

// Содержит изначальные данные приложения.
object TableData {

    // Список кинотеатров.
    val cinemasList =
        listOf(
            Cinema(name = "AMC Empire 25", location = "234 W 42nd St"),
            Cinema(name = "Regal Times Square", location = "247 W 42nd St"),
            Cinema(name = "AMC Lincoln Square 13", location = "1998 Broadway"),
            Cinema(name = "AMC 34th Street 14", location = "312 W 34th St"),
        )

    // Список фильмов.
    val moviesList =
        listOf(
            Movies(
                title = "The Neon Demon 2016",
                year = 2016,
                length = 104,
                description =
                "An aspiring model, Jesse, is new to Los Angeles. However, her beauty and youth, which generate intense fascination and jealousy within the fashion industry, may prove themselves sinister.",
                poster = R.drawable.the_neon_demon_2016,
                price = 20.0f
            ),
            Movies(
                title = "Django Unchained",
                year = 2012,
                length = 115,
                description =
                "With the help of a German bounty-hunter, a freed slave sets out to rescue his wife from a brutal plantation owner in Mississippi.",
                poster = R.drawable.django_unchained_2012,
                price = 25.0f
            ),
            Movies(
                title = "Pulp Fiction",
                year = 1994,
                length = 120,
                description =
                "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                poster = R.drawable.pulp_fiction_1994,
                price = 30.0f
            ),
            Movies(
                title = "The Revenant",
                year = 2015,
                length = 125,
                description =
                "A frontiersman on a fur trading expedition in the 1820s fights for survival after being mauled by a bear and left for dead by members of his own hunting team.",
                poster = R.drawable.the_revenant_2015,
                price = 22.0f
            ),
            Movies(
                title = "The Wolf of Wall Street",
                year = 2013,
                length = 110,
                description =
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                poster = R.drawable.the_wolf_of_wall_street_2013,
                price = 25.0f
            ),
            Movies(
                title = "The Grand Budapest Hotel",
                year = 2014,
                length = 130,
                description =
                "A writer encounters the owner of an aging high-class hotel, who tells him of his early years serving as a lobby boy in the hotel's glorious years under an exceptional concierge.",
                poster = R.drawable.the_grand_budapest_hotel_2014,
                price = 12.0f
            ),
            Movies(
                title = "The Arrival of a Train",
                year = 1896,
                length = 6,
                description = "A train arrives at La Ciotat station.",
                poster = R.drawable.the_arrival_of_a_train_1896,
                price = 10.0f
            )
        )
}

// Для создания списка кинотеатров.
data class Cinema(val name: String, val location: String)
