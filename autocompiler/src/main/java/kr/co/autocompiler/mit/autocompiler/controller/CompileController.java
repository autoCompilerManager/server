package kr.co.autocompiler.mit.autocompiler.controller;

import kr.co.autocompiler.mit.autocompiler.model.CompileReq;
import kr.co.autocompiler.mit.autocompiler.model.CompileRes;
import kr.co.autocompiler.mit.autocompiler.util.CompilerService;


public class CompileController {
    
    public CompileRes compileExecute(CompileReq compileReq){

        String sourceCode = compileReq.getSourceCode();
        String language = compileReq.getLanguage();
  
        CompileRes compileRes = new CompileRes();

          // Java 언어인 경우에 대한 처리 (다른 언어도 추가 가능)
          if ("Java".equalsIgnoreCase(language)) {
            try {
                String compiledResult = CompilerService.compileJavaCode(sourceCode);
                System.out.printf("\n/////////////nServer:: responseMessage : %s\n ////////////\n",compiledResult);
              
                compileRes.setResponseCode(0000);
                compileRes.setResponseMsg(compiledResult);
                compileRes.setCurrentStatus("Finish");
            } catch (Exception e) {
                // TODO: handle exception
                compileRes.setResponseCode(0001);
              compileRes.setResponseMsg("compileFailed");
              compileRes.setCurrentStatus("Finish");
            }
              
              return compileRes;
          } else {
              return null;
          }
    }
}
