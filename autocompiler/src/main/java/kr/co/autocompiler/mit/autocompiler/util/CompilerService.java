package kr.co.autocompiler.mit.autocompiler.util;

import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompilerService {
    

    public static String compileJavaCode(String sourceCode) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // 메모리에 컴파일된 클래스를 담을 객체 생성
        InMemoryJavaFileManager fileManager = new InMemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));

        // 컴파일할 소스 코드 생성
        SimpleJavaFileObject sourceFile = new InMemoryJavaSourceFile("Main", sourceCode);

        // 컴파일 작업 생성
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, Arrays.asList(sourceFile));

        // 컴파일 실행
        boolean success = task.call();

        // 컴파일 결과를 문자열로 반환
        if (success) {
            byte[] classBytes = fileManager.getClassBytes().get("Main");
            return Base64.getEncoder().encodeToString(classBytes);
        } else {
            return "Compilation failed!";
        }
    }

    // public static String compileJavaCode(String sourceCode) {
    //     JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    //     // 메모리에 컴파일된 클래스를 담을 객체 생성
    //     InMemoryJavaFileManager fileManager = new InMemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));

    //     // 컴파일할 소스 코드 생성
    //     SimpleJavaFileObject sourceFile = new InMemoryJavaSourceFile("Main", sourceCode);

    //     // 컴파일 작업 생성
    //     JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, Arrays.asList(sourceFile));

    //     // 컴파일 실행
    //     boolean success = task.call();

    //     // 컴파일 결과를 문자열로 반환
    //     if (success) {
    //         byte[] classBytes = fileManager.getClassBytes().get("Main");
    //         return Base64.getEncoder().encodeToString(classBytes);
    //     } else {
    //         return "Compilation failed!";
    //     }
    // }

    // public static String compileJavaCode(String sourceCode) {
    //     // JavaCompiler 인스턴스 생성
    //     JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        
    //     // 메모리에 컴파일된 클래스를 담을 객체 생성
    //     InMemoryJavaFileManager fileManager = new InMemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));
        
    //     // 컴파일할 소스 코드 생성
    //     SimpleJavaFileObject sourceFile = new InMemoryJavaSourceFile("Main", sourceCode);
        
    //     // 컴파일 작업 생성
    //     JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, List.of(sourceFile));
        
    //     // 컴파일 실행
    //     boolean success = task.call();
    //     // 컴파일 결과를 문자열로 반환
    //     if (success) {
    //         byte[] compiledBytes = fileManager.getClassBytes().get("Main");

    //         // 디버깅을 위해 바이트 배열 출력
    //         // System.out.println("Compiled Class Bytes: " + Arrays.toString(compiledBytes));

    //         // 다양한 인코딩 시도
    //         String compiledString = null;

    //             try {
    //                 compiledString = new String(compiledBytes, "UTF-8");
    //                 System.out.println("Decoding with encoding " + "UTF-8" + " successful.");
    //                 System.out.printf("compileResult:: %s",compiledString);
    //             } catch (UnsupportedEncodingException e) {
    //             }
            

    //         if (compiledString != null) {
    //             return compiledString;
    //         } else {
    //             return "Failed to decode the compiled class content!";
    //         }
    //     } else {
    //         return "Compilation failed!";
    //     }
    // }

    private static class InMemoryJavaSourceFile extends SimpleJavaFileObject {
        private final String code;

        public InMemoryJavaSourceFile(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

    private static class InMemoryJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
        private final Map<String, byte[]> classBytes = new HashMap<>();

        public InMemoryJavaFileManager(StandardJavaFileManager fileManager) {
            super(fileManager);
        }

        public Map<String, byte[]> getClassBytes() {
            return classBytes;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            return new SimpleJavaFileObject(URI.create("string:///" + className.replace('.', '/') + kind.extension), kind) {
                @Override
                public OutputStream openOutputStream() {
                    return new ByteArrayOutputStream() {
                        @Override
                        public void close() throws IOException {
                            super.close();
                            classBytes.put(className, toByteArray());
                        }
                    };
                }
            };
        }
    }
}
