package fr.epita.android.tp2_hello_games_nathan_pauron_01_10_2019

class Game{
    val id      : Int
    val name    : String
    val picture : String

    constructor(id: Int, name: String, picture: String) {
        this.id = id
        this.name = name
        this.picture = picture
    }
}