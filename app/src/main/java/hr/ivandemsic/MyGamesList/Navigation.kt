package hr.ivandemsic.MyGamesList

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hr.ivandemsic.MyGamesList.Routes.AddGame
import hr.ivandemsic.MyGamesList.ui.theme.GameDetails
import hr.ivandemsic.MyGamesList.ui.theme.GameViewModel
import hr.ivandemsic.MyGamesList.ui.theme.MainScreen
import hr.ivandemsic.MyGamesList.ui.theme.StatsScreen
import hr.ivandemsic.MyGamesList.ui.theme.firebaseUI

object Routes{
    const val MainScreen="MainScreen"
    const val StatsScreen="StatsScreen"
    const val GameDetails="GameDetails/{gameId}"
    const val AddGame="AddGame"

    fun getGamesDetailsPath(gameId:Int?):String{
        if (gameId!=null && gameId!=-1){
            return "GameDetails/$gameId"
        }
        return "GameDetails/0"
    }
}


@Composable
fun NavigationController(viewModel: GameViewModel) {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MainScreen){
        composable(Routes.MainScreen){
            MainScreen(navigation=navController,viewModel = viewModel)
        }
        composable(Routes.StatsScreen){
            StatsScreen(navigation = navController)
        }
        composable(Routes.GameDetails,
            arguments = listOf(navArgument("gameId")
            {type= NavType.IntType}))
        {
            backStackEntry -> backStackEntry.arguments?.getInt("gameId")?.let { GameDetails(viewModel,navController,gameId=it)
         }
        }
        composable(Routes.AddGame){
            firebaseUI(LocalContext.current, navigation=navController)
        }
    }
}