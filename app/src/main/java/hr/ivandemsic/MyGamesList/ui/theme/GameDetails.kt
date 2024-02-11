package hr.ivandemsic.MyGamesList.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import hr.ivandemsic.MyGamesList.R
import hr.ivandemsic.MyGamesList.data.Game
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun GameDetails(viewModel: GameViewModel,navigation: NavController,gameId:Int) {

    val game=viewModel.gamesData[gameId]
    var isActive by remember { mutableStateOf(false) }
    var elapsedTime by remember {
        mutableStateOf(0)
    }


        Scaffold(bottomBar = {
            Row (modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                IconButton(iconResource = R.drawable.badge, text = "Stats",navigation,"StatsScreen")
                IconButton(iconResource = R.drawable.home, text = "Home",navigation,"MainScreen")
                IconButton(iconResource = R.drawable.plus, text = "Add game",navigation,"AddGame")
            }
        }
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Blue1, Blue2),
                            startY = 100f
                        )
                    )
            ) {
                Column {
                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                        Text(
                            text = game.title,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 25.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ){
                        Image(
                            painter = rememberAsyncImagePainter(model = game.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )


                }
                    Text(text = "Time played: ${formatTime(game.timePlayed)}\n" +
                            "Achievements: ${game.completed_Achievements}/${game.achievements}\n" +"Description:"
                        ,modifier=Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )) {
                        Text(text = "${game.description}\n"
                            ,modifier=Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Normal))

                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LaunchedEffect(isActive) {
                            while (isActive) {
                                delay(1000)
                                elapsedTime += 1
                            }
                        }
                        Button(onClick = {
                            isActive=!isActive
                            val currentTimeString = game.timePlayed
                            val currentTime = currentTimeString?.toIntOrNull() ?: 0
                            var updatedTime=currentTime+elapsedTime

                            viewModel.updateTimePlayed(gameId, updatedTime.toString())
                            viewModel.updateGameInFirestore(gameId, updatedTime.toString(),"timePlayed")
                        }, modifier = Modifier.padding(vertical = 13.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Gray1)) {
                            Text(if (!isActive) "Start time" else "Stop time")
                        }
                        Button(onClick = {

                            val currentNumberString = game.completed_Achievements
                            val currentNumber = currentNumberString?.toIntOrNull() ?: 0
                            val updatedAchievement = currentNumber + 1

                            viewModel.updateCompletedAchievements(gameId, updatedAchievement.toString())

                            viewModel.updateGameInFirestore(gameId, updatedAchievement.toString(),"completed_Achievements")
                        }, modifier = Modifier.padding(horizontal = 4.dp, vertical = 13.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Gray1)) {
                            Text("Update achievements")
                        }
                    }

            }
        }
    }
}

fun formatTime(seconds: String): String {
    val hours = seconds.toInt() / 3600
    val minutes = (seconds.toInt() % 3600) / 60
    val remainingSeconds = seconds.toInt() % 60

    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}









