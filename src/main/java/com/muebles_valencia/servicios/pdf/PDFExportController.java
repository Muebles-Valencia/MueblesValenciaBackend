package com.muebles_valencia.servicios.pdf;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muebles_valencia.entidades.factura.Factura;
import com.muebles_valencia.entidades.producto.Producto;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "null" })
@RequestMapping("/pdfs")
public class PDFExportController {

	@Autowired
	private PDFGeneratorService pdfService;
	
	
	@GetMapping("/pdf/generate")
	public void generatePDF(HttpServletResponse response , List<Producto> productos) {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey="Content-Disposition";
		String headerValue = "attachment; filename=pdf_"+currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		
		try {
			pdfService.export(response , productos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
