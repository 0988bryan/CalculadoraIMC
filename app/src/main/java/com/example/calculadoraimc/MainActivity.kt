package com.example.calculadoraimc

import CalculadoraIMCApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType //agregamos el import para el nav controller
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CalculadoraIMCApp()
                }
            }
        }
    }
}



@Composable
fun PantallaEntrada(navController: androidx.navigation.NavController) {
    var nombre by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Calculadora de IMC", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = peso, onValueChange = { peso = it }, label = { Text("Peso (kg)") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = altura, onValueChange = { altura = it }, label = { Text("Altura (m)") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val p = peso.toFloatOrNull()
            val a = altura.toFloatOrNull()
            if (p != null && a != null && p > 0 && a > 0) {
                val imc = p / (a * a)
                navController.navigate("resultado/$nombre/$imc")
            } else {
                error = true
            }
        }) { Text("Calcular") }

        if (error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Por favor, ingresa valores válidos", color = Color.Red)
        }
    }
}

@Composable
fun PantallaResultado(navController: androidx.navigation.NavController, nombre: String, imc: Float) {
    val categoria: String
    val color: Color

    when {
        imc < 18.5 -> { categoria = "Bajo peso"; color = Color.Red }
        imc < 25.0 -> { categoria = "Peso normal"; color = Color.Green }
        imc < 30.0 -> { categoria = "Sobrepeso"; color = Color(0xFFFF9800) } // Naranja
        else -> { categoria = "Obesidad"; color = Color.Red }
    }

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Hola $nombre,", style = MaterialTheme.typography.headlineSmall)
        Text("tu resultado es:", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("IMC: ${"%.1f".format(imc)}", style = MaterialTheme.typography.displayMedium)
        Text(categoria, color = color, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}