package com.example.listas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast

import com.example.listas.ui.theme.ListasTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListasTheme {
                ListSample4()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSample4(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    var nombreCurso by remember { mutableStateOf(value = "") }
    var idCurso by remember { mutableStateOf(value = "") }

    val cursos = remember {
        mutableStateListOf<Curso>().apply {
            for (i in 1..100) {
                add(Curso(i, nombre = "Nombre $i", descripcion = "Descripcion $i"))
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Código: Modificar elemento de la lista") }) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            OutlinedTextField(
                label = { Text(text = "Id") },
                value = idCurso,
                onValueChange = { idCurso = it.replace(Regex("[^0-9]"), "") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                label = { Text(text = "Nombre del curso") },
                value = nombreCurso,
                onValueChange = { nombreCurso = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val id = idCurso.toIntOrNull()

                if (id == null || id < 1 || id > cursos.size) {
                    Toast.makeText(context, "ID no válido o fuera de rango (1-100)", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val index = id - 1

                val cursoAntiguo = cursos[index]
                val cursoModificado = cursoAntiguo.copy(nombre = nombreCurso)

                cursos[index] = cursoModificado

                Toast.makeText(context, "Curso #$id modificado con éxito!", Toast.LENGTH_SHORT).show()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Modificar Curso")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(cursos) { item ->

                    Text(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .background(if (item.id % 2 == 0) Color(0xFFFFCC80) else Color(0xFFFFE0B2))
                            .padding(8.dp),
                        text = "ID: ${item.id} | Nombre: ${item.nombre} | ${item.descripcion}"
                    )
                }
            }
        }
    }
}
