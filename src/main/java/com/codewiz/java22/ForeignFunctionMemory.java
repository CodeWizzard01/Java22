package com.codewiz.java22;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class ForeignFunctionMemory {

    public static void main(String[] args) throws Throwable {

        // declaration of the strlen C standard library function - size_t strlen(const char *s);
        String s = "Hello, World!";
        try (Arena arena = Arena.ofConfined()) {

            // Allocate off-heap memory and
            // Storing into off-heap memory
            MemorySegment nativeString = arena.allocateFrom(s);

            // Obtain an instance of the native linker
            Linker linker = Linker.nativeLinker();

            // Locate the address of the C function signature
            SymbolLookup stdLib = linker.defaultLookup();
            MemorySegment strlen_addr = stdLib.find("strlen").get();

            // Create a description of the C function
            FunctionDescriptor strlen_sig =
                    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS);

            // Create a downcall handle for the C function
            MethodHandle strlen = linker.downcallHandle(strlen_addr, strlen_sig);

            // Call the C function directly from Java
            long length = (long)strlen.invokeExact(nativeString);
            System.out.println("The length of the string is " + length);
        }
    }
}
