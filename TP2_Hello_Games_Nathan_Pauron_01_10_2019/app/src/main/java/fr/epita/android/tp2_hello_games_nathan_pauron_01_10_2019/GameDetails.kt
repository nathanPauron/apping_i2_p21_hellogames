package fr.epita.android.tp2_hello_games_nathan_pauron_01_10_2019

class GameDetails{
    val id              : Int
    val name            : String
    val type            : String
    val players         : Int
    val year            : Int
    val url             : String
    val picture         : String
    val description_fr  : String
    val description_en   : String

    constructor(
        id: Int,
        name: String,
        type: String,
        players: Int,
        year: Int,
        url: String,
        picture: String,
        description_fr: String,
        desciption_en: String
    ) {
        this.id = id
        this.name = name
        this.type = type
        this.players = players
        this.year = year
        this.url = url
        this.picture = picture
        this.description_fr = description_fr
        this.description_en = desciption_en
    }
}