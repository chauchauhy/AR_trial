package com.example.ar_trial;

import androidx.appcompat.app.AppCompatActivity;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {
    private ArFragment arFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MacAvtTag", "createdView");
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.mainActFragmentHost);

        // two method to work, first is create a red ball ar object, second is add an ar object(custom) to the screen
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.RED))
                    .thenAccept(material ->
                    {
                        ModelRenderable renderable = ShapeFactory.makeSphere(1.0f, new Vector3(0f, 1f, 1f), material);
                        AnchorNode node = new AnchorNode(anchor);
                        node.setRenderable(renderable);
                        arFragment.getArSceneView().getScene().addChild(node);
                    });
        }));

//        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
//            Anchor anchor = hitResult.createAnchor();
//            ModelRenderable.builder()
//                    .setSource(this, Uri.parse("TcoToucan.sfb"))
//                    .build()
//                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable));
//            Log.i("MacAvtTag", "MainFClicked");
//
//
//        }));
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode node = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(node);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(node);
        transformableNode.select();


    }
}