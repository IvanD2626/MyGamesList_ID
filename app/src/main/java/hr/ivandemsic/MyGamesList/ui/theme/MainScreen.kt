@file:OptIn(ExperimentalMaterial3Api::class)

package hr.ivandemsic.MyGamesList.ui.theme

import android.annotation.SuppressLint
import android.graphics.Picture
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import hr.ivandemsic.MyGamesList.R
import hr.ivandemsic.MyGamesList.R.*
import hr.ivandemsic.MyGamesList.Routes
import hr.ivandemsic.MyGamesList.data.Game
import hr.ivandemsic.MyGamesList.data.gameList
import java.nio.InvalidMarkException
import java.text.SimpleDateFormat




@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navigation: NavController,viewModel:GameViewModel) {
    Scaffold(bottomBar = {
        Row (modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
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
                                colors = listOf(Blue1, Blue2)
                            )
                        )
                ) {
                    Column {
                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            Text(
                                text = "MyGamesList",
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
                        GamesList(viewModel,navigation)

                        }
            }
            }
    }




@Composable
fun IconButton(
    @DrawableRes iconResource: Int,
    text:String,
    navigation: NavController,
    screen:String
) {
    Button(
        onClick = { navigation.navigate(route = screen) },
        colors = ButtonDefaults.buttonColors(containerColor = Gray1),
        modifier = Modifier.padding(horizontal = 3.dp, vertical = 13.dp)
    ) {
        Row {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = text
            )
            Spacer(Modifier.width(2.dp))
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }
    }

}

@Composable
fun GamesList(viewModel:GameViewModel,navigation: NavController) {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(650.dp)
    ) {
        items(viewModel.gamesData.size) {
            GameIcon(image = viewModel.gamesData[it].image, title = viewModel.gamesData[it].title) {
                navigation.navigate(
                    Routes.getGamesDetailsPath(it)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}







    


