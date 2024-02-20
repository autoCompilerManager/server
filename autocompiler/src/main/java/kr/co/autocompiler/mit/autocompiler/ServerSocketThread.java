package kr.co.autocompiler.mit.autocompiler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.autocompiler.mit.autocompiler.controller.CompileController;
import kr.co.autocompiler.mit.autocompiler.model.CompileReq;
import kr.co.autocompiler.mit.autocompiler.model.CompileRes;

public class ServerSocketThread extends Thread {
    
    private ServerSocket serverSocket;

    public ServerSocketThread() {
        try {
            // 서버 소켓 생성 및 포트 지정
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 클라이언트의 연결을 대기하고 소켓 생성
                Socket socket = serverSocket.accept();

                // 클라이언트와의 통신을 위한 입력 및 출력 스트림 생성
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

               // 클라이언트로부터 데이터 수신
               String receivedData = inputStream.readUTF();

                   String receivedString = (String) receivedData;
                   System.out.println("Received String from client: " + receivedString);

                try {
                        // JSON 문자열을 객체로 변환
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(receivedString);

                        // 필요한 값을 추출
                        String language = jsonNode.get("language").asText();
                        String sourceCode = jsonNode.get("sourceCode").asText();

                            // 추출된 값 출력
                        System.out.println("Language: " + language);
                        System.out.println("Source Code: " + sourceCode);

                        CompileController compileController = new CompileController();
                        CompileReq  compileReq = new CompileReq();
                        compileReq.setLanguage(language);
                        compileReq.setSourceCode(sourceCode);
                        CompileRes compileRes = compileController.compileExecute(compileReq);

                        // System.out.println("REPO:: responseCode :" + compileRes.getResCode());
                        // System.out.println("REPO:: responseMsg :" + compileRes.getResponseMsg());
                        // System.out.println("REPO:: currentStautus :" + compileRes.getCurrentStatus());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                // 클라이언트에 데이터 전송
                outputStream.writeObject("Client sent to Server Data :: ");


                    // 리소스 해제
                    inputStream.close();
                    outputStream.close();
                    // socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
