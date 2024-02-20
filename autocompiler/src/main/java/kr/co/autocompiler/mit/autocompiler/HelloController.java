package kr.co.autocompiler.mit.autocompiler;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kr.co.autocompiler.mit.autocompiler.model.CompileReq;
import kr.co.autocompiler.mit.autocompiler.model.CompileRes;
import kr.co.autocompiler.mit.autocompiler.controller.CompileController;

@RestController
public class HelloController {
	
	private CompileController compileController = new CompileController();

	// @PostMapping("/hello")

	@RequestMapping("/compileRequest")
	@PostMapping
	public CompileRes compileRequest(@RequestBody  CompileReq compileReq) {

		CompileController compileController = new CompileController();
		
		CompileReq data = compileReq;
		System.out.printf("sourceCode : %s, language : %s", data.getSourceCode(),data.getLanguage());
		CompileRes compileRes = compileController.compileExecute(compileReq);

		return compileRes;
	}
}
