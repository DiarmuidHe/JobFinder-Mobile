package com.example.jobfinder.ui.screens

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.coroutines.resume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeCameraScreen(
    jobId: String,
    onBack: () -> Unit,
    onPhotoCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember { mutableStateOf(false) }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
        if (!granted) {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    val imageCapture = remember {
        ImageCapture.Builder()
            .setTargetRotation(android.view.Surface.ROTATION_0)
            .build()
    }

    var previewView: PreviewView? by remember { mutableStateOf(null) }

    LaunchedEffect(hasCameraPermission) {
        if (!hasCameraPermission) return@LaunchedEffect

        val cameraProvider = context.getCameraProvider()
        val preview = androidx.camera.core.Preview.Builder().build().also {
            it.setSurfaceProvider(previewView?.surfaceProvider)
        }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Capture Resume") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        if (!hasCameraPermission) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Waiting for camera permission…")
            }
            return
        }

        // Camera preview
        AndroidView(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            factory = { ctx ->
                PreviewView(ctx).also { previewView = it }
            }
        )

        // Capture button
        Button(
            onClick = {
                val name = "resume_${
                    SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                        .format(System.currentTimeMillis())
                }"

                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, name)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/JobFinder")
                    }
                }

                // ✅ PASS THE COLLECTION URI, NOT A ROW URI
                val outputOptions = ImageCapture.OutputFileOptions.Builder(
                    context.contentResolver,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                ).build()

                val executor = ContextCompat.getMainExecutor(context)

                imageCapture.takePicture(
                    outputOptions,
                    executor,
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(
                            outputFileResults: ImageCapture.OutputFileResults
                        ) {
                            // This is the actual row URI that CameraX inserted
                            val savedUri = outputFileResults.savedUri

                            Toast.makeText(
                                context,
                                "Resume photo captured",
                                Toast.LENGTH_SHORT
                            ).show()

                            if (savedUri != null) {
                                onPhotoCaptured(savedUri)
                            } else {
                                // Fallback if savedUri is null (rare)
                                Toast.makeText(
                                    context,
                                    "Image saved, but URI is null",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Toast.makeText(
                                context,
                                "Capture failed: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text("Take Resume Photo", style = MaterialTheme.typography.titleMedium)
        }
    }
}

// helper to get camera provider as a suspend function
private suspend fun android.content.Context.getCameraProvider(): ProcessCameraProvider =
    suspendCancellableCoroutine { cont ->
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(
            {
                cont.resume(cameraProviderFuture.get())
            },
            ContextCompat.getMainExecutor(this)
        )
    }
