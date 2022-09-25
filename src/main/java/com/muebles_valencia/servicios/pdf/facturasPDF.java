package com.muebles_valencia.servicios.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.muebles_valencia.entidades.factura.Factura;

@Component("/listaFacturas")
public class facturasPDF extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura2 = new Factura();
		@SuppressWarnings("unchecked")
		List<Factura> facturas = (List<Factura>) model.get("Factura");
		
		PdfPTable tablaFacturas = new PdfPTable(8);
		String s = "";
		facturas.forEach(factura -> {
			tablaFacturas.addCell(factura.getCodigo_factura().toString());
			tablaFacturas.addCell(factura.getNombre_cliente());
			tablaFacturas.addCell(factura.getNombre_producto());
			tablaFacturas.addCell(s.valueOf(factura.getCantidad_producto()));
			tablaFacturas.addCell(factura.getFecha_factura().toString());
			tablaFacturas.addCell(s.valueOf(factura.getTelefono_cliente()));
			tablaFacturas.addCell(factura.getTotal_a_pagar());
		});
		document.add(tablaFacturas);
	}
	
	
}
