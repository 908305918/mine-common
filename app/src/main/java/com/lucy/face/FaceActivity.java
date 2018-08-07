package com.lucy.face;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lucy.common.R;

import java.util.List;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.FotoapparatSwitcher;
import io.fotoapparat.facedetector.Rectangle;
import io.fotoapparat.facedetector.processor.FaceDetectorProcessor;
import io.fotoapparat.facedetector.view.RectanglesView;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.log.Loggers.fileLogger;
import static io.fotoapparat.log.Loggers.logcat;
import static io.fotoapparat.log.Loggers.loggers;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.lensPosition;

public class FaceActivity extends Activity {
    private boolean hasCameraPermission;
    private CameraView cameraView;
    private RectanglesView rectanglesView;

    private FotoapparatSwitcher fotoapparatSwitcher;
    private Fotoapparat frontFotoapparat;
    private Fotoapparat backFotoapparat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_face);

        cameraView = (CameraView) findViewById(R.id.camera);
        rectanglesView = (RectanglesView) findViewById(R.id.rectangles);

        cameraView.setVisibility(View.VISIBLE);

        frontFotoapparat = createFotoapparat(LensPosition.FRONT);
        backFotoapparat = createFotoapparat(LensPosition.BACK);
        fotoapparatSwitcher = FotoapparatSwitcher.withDefault(backFotoapparat);

    }

    private boolean canSwitchCameras() {
        return frontFotoapparat.isAvailable() == backFotoapparat.isAvailable();
    }

    private Fotoapparat createFotoapparat(LensPosition position) {
        return Fotoapparat
                .with(this)
                .into(cameraView)
                .lensPosition(lensPosition(position))
                .frameProcessor(
                        FaceDetectorProcessor.with(this)
                                .listener(new FaceDetectorProcessor.OnFacesDetectedListener() {
                                    @Override
                                    public void onFacesDetected(List<Rectangle> faces) {
                                        Log.d("&&&", "Detected faces: " + faces.size());

                                        rectanglesView.setRectangles(faces);
                                    }
                                })
                                .build()
                )
                .logger(loggers(
                        logcat(),
                        fileLogger(this)
                ))
                .build();
    }

    private void switchCamera() {
        if (fotoapparatSwitcher.getCurrentFotoapparat() == frontFotoapparat) {
            fotoapparatSwitcher.switchTo(backFotoapparat);
        } else {
            fotoapparatSwitcher.switchTo(frontFotoapparat);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasCameraPermission) {
            fotoapparatSwitcher.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (hasCameraPermission) {
            fotoapparatSwitcher.stop();
        }
    }
}
