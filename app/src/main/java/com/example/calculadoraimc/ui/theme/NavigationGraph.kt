import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calculadoraimc.PantallaEntrada
import com.example.calculadoraimc.PantallaResultado
import CalculadoraIMCApp
@Composable
fun CalculadoraIMCApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pantalla1") {
        composable("pantalla1") { PantallaEntrada(navController) }
        composable(
            route = "resultado/{nombre}/{imc}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("imc") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val imc = backStackEntry.arguments?.getFloat("imc") ?: 0f
            PantallaResultado(navController, nombre, imc)
        }
    }
}
fun calcularIMC(peso: Float, altura: Float): Float {
    return peso / (altura * altura)
}