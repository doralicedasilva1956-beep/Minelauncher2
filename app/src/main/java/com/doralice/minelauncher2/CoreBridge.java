package com.doralice.minelauncher2;

public class CoreBridge {
    static {
        // Carrega a biblioteca nativa C++ que configuramos no CMake
        System.loadLibrary("minelauncher2");
    }

    // Avisa o Java que esta função está escrita lá no arquivo C++ (native-lib.cpp)
    public static native boolean loadNativeLibrary(String libraryPath);
}
