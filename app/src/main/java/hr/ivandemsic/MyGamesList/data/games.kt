package hr.ivandemsic.MyGamesList.data

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FirebaseFirestore
import hr.ivandemsic.MyGamesList.R

data class Game(
    var id: String ="",
    var image: String = "",
    var title: String = "",
    var description: String = "",
    var achievements: String="",
    var completed_Achievements:String="0",
    var timePlayed: String="0"
)

data class Personal(
    var games:Int,
    var sumTime: Int,
    var maxAchievements: Int,

)


var gameList = mutableStateListOf<Game?>()


/*https://cdn.akamai.steamstatic.com/steam/apps/1273400/header.jpg?t=1703169958*/
