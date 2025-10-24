**EXPLICACIÓN DE EJERCICIOS**

**Integrantes:**

William Isaias Roque Quispe

Misael Josias Marron Lope

**Código: Lista oculta en Tab** 

**Enlace: [**https://github.com/WilliamIsaiasRoque/IDNP_TEO/tree/main/lista_oculta**](https://github.com/WilliamIsaiasRoque/IDNP_TEO/tree/main/lista_oculta)**

La estructura se basa en dos capas clave: el contenedor de pestañas (HorizontalPager) y el contenido de lista (LazyColumn). Al iniciar la aplicación, la MainActivity carga el Composable DemoLazyLoadingTabs que establece dos pestañas ("Tab 1" y "Tab 2"). El estado de qué pestaña está activa se maneja mediante variables (selectedTabIndex) para asegurar que la barra superior (TabRow) y el contenido se sincronicen.

El HorizontalPager, que actúa como el área deslizable principal. Aunque estamos ubicados visualmente en el Tab 1, el HorizontalPager tiene una configuración interna que, por defecto, decide cuánto contenido adyacente debe componer y medir por adelantado. Si bien el objetivo del experimento es demostrar que la lista del Tab 2 no se carga, con la configuración estándar del Pager, este componente puede forzar la composición de la página siguiente para que el deslizamiento sea absolutamente fluido, creando un buffer invisible. Solo si ajustamos explícitamente parámetros de precarga avanzados (que en versiones recientes del código son difíciles de anular totalmente), podríamos evitar la composición del Tab 2 antes de tiempo.

Dentro del Tab 2, el componente LazyColumn es el responsable de gestionar la lista de 50 ítems. La LazyColumn solo ejecuta el código de los Composable de las tarjetas visibles y su pequeño margen de seguridad (prefetching). Por lo tanto, incluso si el HorizontalPager fuerza a que el ContenidoTab2ConLista se componga (lo que se registra con el primer Log.d), la LazyColumn sigue siendo perezosa, y solo compondrá realmente la UI de los ítems uno por uno a medida que la pantalla se desplace, como evidencian los logs individuales de renderizado. 

![](Aspose.Words.6c59f566-b7da-48ae-b69d-cc2637909184.001.png)

**Código: Modificar elemento de la lista**

**Enlace: [**https://github.com/WilliamIsaiasRoque/IDNP_TEO/tree/main/**](https://github.com/WilliamIsaiasRoque/IDNP_TEO/tree/main/)**

El sistema se inicia con la declaración de la lista principal de cien cursos mediante el constructor remember { mutableStateListOf<Curso>() }. Esta sintaxis inscribe en el mecanismo de reactividad de Compose (mutableStateListOf). Esta lista, al ser observable, se convierte en la única fuente de verdad para la lista visible, el LazyColumn.

El proceso de modificación se activa cuando el usuario proporciona un identificador (idCurso) y un nuevo nombre (nombreCurso) a través de los campos de texto y pulsa el botón "Modificar Curso". Dentro de la función onClick, el código valida la entrada y accede al elemento específico de la lista. En este punto, es crucial la operación de actualización: al utilizar el índice para asignar el nuevo objeto de curso (cursos[index] = cursoModificado), el mutableStateListOf detecta el cambio en la estructura interna de la lista.

Esta detección notifica al marco de Compose que la lista ha sido alterada. En respuesta, Compose ejecuta el proceso de recomposición, actualizando eficientemente solo las partes de la interfaz que leen el estado modificado.

