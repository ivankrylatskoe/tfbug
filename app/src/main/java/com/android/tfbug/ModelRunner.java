/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/
package com.android.tfbug;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;


public class ModelRunner  {

    private Interpreter tfLite;

    public ModelRunner(
            final AssetManager assetManager,
            final String modelFilename)
    {
        try {
            Interpreter.Options options = new Interpreter.Options();
            MappedByteBuffer tfliteModel = loadModelFile(assetManager, modelFilename);
            tfLite = new Interpreter(tfliteModel, options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static MappedByteBuffer loadModelFile(AssetManager assets, String modelFilename)
            throws IOException {
        AssetFileDescriptor fileDescriptor = assets.openFd(modelFilename);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    public void runModel(byte[] inputData) {
        // Prepare input data
        ByteBuffer imgData = ByteBuffer.wrap(inputData);
        imgData.order(ByteOrder.nativeOrder());
        Object[] inputArray = new Object[1];
        inputArray[0] = imgData;
        imgData.rewind();

        // Prepare output data
        int[] shape = tfLite.getOutputTensor(0).shape();
        ByteBuffer outData = ByteBuffer.allocateDirect(shape[0] * shape[1] * shape[2]);
        outData.order(ByteOrder.nativeOrder());
        Map<Integer, Object> outputMap = new HashMap<>();
        outputMap.put(0, outData);
        outData.rewind();

        // Run model
        tfLite.runForMultipleInputsOutputs(inputArray, outputMap);

        // Output result bytes
        byte[] outputArray = new byte[60];
        outData.rewind();
        outData.get(outputArray, 0, 60);
        Log.i(MainActivity.TAG, "Model output: \n" + toUint8(outputArray));
    }

    String toUint8(byte [] arr) {
        StringBuilder out = new StringBuilder();
        int n = 0;
        for (byte b : arr) {
            out.append(String.format("%d ", b >= 0 ? b : b + 256));
            n++;
            if (n == 6) {
                out.append("\n");
                n = 0;
            }
        }
        return out.toString();
    }
}
