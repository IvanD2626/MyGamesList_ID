package hr.ivandemsic.MyGamesList.ui.theme

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import hr.ivandemsic.MyGamesList.data.Game



class GameViewModel:ViewModel(){
    private val db = Firebase.firestore

    val gamesData = mutableStateListOf<Game>()

    init {
        fetchDatabaseData()
    }

    private fun fetchDatabaseData(){
        db.collection("Games")
            .get()
            .addOnSuccessListener { result-> for(data in result.documents){
                val game= data.toObject(Game::class.java)
                if(game!=null){
                    game.id=data.id
                    gamesData.add(game)
            } } }
    }

    fun updateCompletedAchievements(gameId: Int, updatedValue: String) {
        gamesData[gameId].completed_Achievements = updatedValue
    }
    fun updateTimePlayed(gameId: Int, updatedValue: String) {
        gamesData[gameId].timePlayed = updatedValue
    }
    fun updateGameInFirestore(gameId: Int, update: String,variable:String) {
        val documentReference = db.collection("Games").document(gamesData[gameId].id)

        documentReference.update(variable, update)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

}