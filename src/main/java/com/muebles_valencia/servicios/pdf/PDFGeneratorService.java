package com.muebles_valencia.servicios.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.muebles_valencia.entidades.factura.Factura;
import com.muebles_valencia.entidades.producto.Producto;

@Service
public class PDFGeneratorService {

	public Document export(HttpServletResponse response, List<Producto> productos) throws IOException, IOException {
		Document document = new Document(PageSize.A4.rotate());

		try {
			PdfWriter.getInstance(document, response.getOutputStream());

			document.open();
			Paragraph p1 = new Paragraph(new Chunk(
					"Muebles Valencia. \n Cra 17 # 21-46 \n Tel. 311 7969946 \n Armenia-Quindio \n valenciamuebles49@gmail.com",
					FontFactory.getFont(FontFactory.HELVETICA, 8)));
			p1.add(new Phrase("                              Muebles Valencia",
					FontFactory.getFont(FontFactory.TIMES_BOLD, 32)));

			p1.setSpacingAfter((float) 80.0);
			document.add(p1);
			String r = "";
			ArrayList<String> bogusData = new ArrayList<String>();
			productos.forEach(prod -> {
				bogusData.add("M0065920");
				bogusData.add(prod.getCodigo_producto().toString());
				bogusData.add(prod.getNombre_producto());
				bogusData.add(prod.getDescripcion_producto());
				bogusData.add(prod.getId_categoria().getNombre_categoria());
				bogusData.add(prod.getNombre_proveedor_producto());
				bogusData.add(r.valueOf(prod.getPrecio_producto()));	
			});
			System.out.println("BOGUS DATA " + bogusData);
			int NumColumns = 7;
			PdfPTable datatable = new PdfPTable(NumColumns);
			int[] headerwidths = { 15, 15, 15, 15, 15, 15 , 15}; // percentage
			datatable.setWidths(headerwidths);
			datatable.setWidthPercentage(100); // percentage
			datatable.getDefaultCell().setPadding(3);
			datatable.getDefaultCell().setBorderWidth(2);
			datatable.getDefaultCell().setHorizontalAlignment(Paragraph.ALIGN_CENTER);
			datatable.addCell("NUMERO PRODUCTO COMPRADO");
			datatable.addCell("CODIGO PRODUCTO");
			datatable.addCell("NOMBRE PRODUCTO");
			datatable.addCell("DESCRIPCION PRODUCTO");
			datatable.addCell("CATEGORIA PRODUCTO");
			datatable.addCell("NOMBRE PROVEEDOR");
			datatable.addCell("PRECIO POR UNIDAD");
			
			datatable.setHeaderRows(1); // this is the end of the table header

			datatable.getDefaultCell().setBorderWidth(1);
			int contador = 1;
			System.out.println("TAMAÑO FACTURA " + productos.size());
			if(productos.size() == 1) {
				for (int i = 1; i <= productos.size(); i++) {
					if (i % 2 == 1) {
						datatable.getDefaultCell().setGrayFill(0.9f);
					}
					for (int x = 0; x < bogusData.size(); x++) {

						if (bogusData.get(x).equals("M0065920")) {
							datatable.addCell(contador + "");
							contador = contador + 1;
						} else {
							
							datatable.addCell(bogusData.get(x));
						}

					}
					if (i % 2 == 1) {
						datatable.getDefaultCell().setGrayFill(1);
					}
				}
				document.add(datatable);
				bogusData.clear();
			}else {
				for (int i = 1; i < productos.size(); i++) {
					if (i % 2 == 1) {
						datatable.getDefaultCell().setGrayFill(0.9f);
					}
					for (int x = 0; x < bogusData.size(); x++) {

						if (bogusData.get(x).equals("M0065920")) {
							datatable.addCell(contador + "");
							contador = contador + 1;
						} else {
							
							datatable.addCell(bogusData.get(x));
						}

					}
					if (i % 2 == 1) {
						datatable.getDefaultCell().setGrayFill(1);
					}
				}
				document.add(datatable);
				bogusData.clear();

			}
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
		return document;
	}
	
	public Document exportPDFInvoice(HttpServletResponse response, Factura factura) throws IOException, IOException {
		Document document = new Document(PageSize.A4.rotate());

		try {
			PdfWriter.getInstance(document, response.getOutputStream());

			document.open();
			Paragraph p1 = new Paragraph(new Chunk(
					"Muebles Valencia. \n Cra 17 # 21-46 \n Tel. 311 7969946 \n Armenia-Quindio \n valenciamuebles49@gmail.com",
					FontFactory.getFont(FontFactory.HELVETICA, 8)));
			p1.add(new Phrase("                              Muebles Valencia",
					FontFactory.getFont(FontFactory.TIMES_BOLD, 32)));

			p1.setSpacingAfter((float) 80.0);
			document.add(p1);
			String r = "";
			ArrayList<Factura> facturas = new ArrayList<Factura>();
			facturas.add(factura);
			ArrayList<String> bogusData = new ArrayList<String>();
//			bogusData.add("M0065920");
			bogusData.add(factura.getCodigo_factura().toString());
			bogusData.add(factura.getNombre_cliente());
			bogusData.add(factura.getCedula_cliente());
			bogusData.add(factura.getNombre_producto());
			bogusData.add(factura.getTelefono_cliente());
			bogusData.add(factura.getCantidad_producto());	
			bogusData.add(factura.getFecha_factura());
			bogusData.add(factura.getTotal_a_pagar());
			System.out.println("BOGUS DATA " + bogusData);
			int NumColumns = 8;
			PdfPTable datatable = new PdfPTable(NumColumns);
			int[] headerwidths = { 15, 15, 15, 15, 15 , 15 , 15 , 15}; // percentage
			datatable.setWidths(headerwidths);
			datatable.setWidthPercentage(100); // percentage
			datatable.getDefaultCell().setPadding(3);
			datatable.getDefaultCell().setBorderWidth(2);
			datatable.getDefaultCell().setHorizontalAlignment(Paragraph.ALIGN_CENTER);
			datatable.addCell("CODIGO FACTURA");
			datatable.addCell("NOMBRE CLIENTE");
			datatable.addCell("CEDULA CLIENTE");
			datatable.addCell("NOMBRE PRODUCTO");
			datatable.addCell("TELEFONO CLIENTE");
			datatable.addCell("CANTIDAD PRODUCTO");
			datatable.addCell("FECHA FACTURA");
			datatable.addCell("TOTAL A PAGAR");
			datatable.setHeaderRows(1); // this is the end of the table header

			datatable.getDefaultCell().setBorderWidth(1);
			int contador = 1;
			System.out.println("TAMAÑO FACTURA " + factura);
			if(facturas.size() == 1) {
				for (int i = 1; i <= facturas.size(); i++) {
					if (i % 2 == 1) {
						datatable.getDefaultCell().setGrayFill(0.9f);
					}
					for (int x = 0; x < bogusData.size(); x++) {

						if (bogusData.get(x).equals("M0065920")) {
							datatable.addCell(contador + "");
							contador = contador + 1;
						} else {
							
							datatable.addCell(bogusData.get(x));
						}

					}
					if (i % 2 == 1) {
						datatable.getDefaultCell().setGrayFill(1);
					}
				}
				document.add(datatable);
				bogusData.clear();
			}else {
				for (int i = 1; i < facturas.size(); i++) {
					if (i % 2 == 1) {
						datatable.getDefaultCell().setGrayFill(0.9f);
					}
					for (int x = 0; x < bogusData.size(); x++) {

						if (bogusData.get(x).equals("M0065920")) {
							datatable.addCell(contador + "");
							contador = contador + 1;
						} else {
							
							datatable.addCell(bogusData.get(x));
						}

					}
					if (i % 2 == 1) {
						datatable.getDefaultCell().setGrayFill(1);
					}
				}
				document.add(datatable);
				bogusData.clear();

			}
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
		return document;
	}
}