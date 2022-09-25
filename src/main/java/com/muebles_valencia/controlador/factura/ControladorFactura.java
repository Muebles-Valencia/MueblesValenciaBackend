package com.muebles_valencia.controlador.factura;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.Document;
import com.muebles_valencia.entidades.cliente.Cliente;
import com.muebles_valencia.entidades.cliente.Rol;
import com.muebles_valencia.entidades.factura.Factura;
import com.muebles_valencia.entidades.factura.FacturaDTO;
import com.muebles_valencia.entidades.producto.Producto;
import com.muebles_valencia.repositorio.carritoCompra.RepositorioCarritoCompra;
import com.muebles_valencia.repositorio.reserva.RepositorioReserva;
import com.muebles_valencia.servicios.carritoCompra.ServicioCarritoCompra;
import com.muebles_valencia.servicios.correo.EnviadorDeCorreos;
import com.muebles_valencia.servicios.facturas.ServicioFacturaImpl;
import com.muebles_valencia.servicios.pdf.PDFExportController;
import com.muebles_valencia.servicios.pdf.PDFGeneratorService;
import com.muebles_valencia.servicios.productos.ServicioProducto;

@RestController
@CrossOrigin(origins = { "https://proyecto-mv.pages.dev", "https://muebleriaback.herokuapp.com",
		"http://localhost:3000", "http://localhost:8080" })
@RequestMapping("/facturas")
public class ControladorFactura {

	@Autowired
	private ServicioFacturaImpl servicioFacturas;

	@Autowired
	private PDFExportController pdfExportController;
	
	@Autowired
	private ServicioProducto servicioProducto;
	
	@Autowired
	private PDFGeneratorService servicePDF;
	
	@Autowired
	private RepositorioCarritoCompra repoCarrito;
	
	// Consultar factura en la base de datos
	// http://localhost:8080/facturas/consultarFactura/{id}
	@GetMapping("/consultarFactura/{id}")
	public void consultarFactura(HttpServletResponse response, @PathVariable(value = "id") int codigo) throws IOException {
		Optional<Factura> factura = servicioFacturas.findById(codigo);
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey="Content-Disposition";
		String headerValue = "attachment; filename=pdf_"+currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		if (!factura.isPresent()) {
			System.out.println("FACTURA NO PRESENTE " + factura.get());
		}
		System.out.println("FACTURA EN CONSULTAR " + factura.get());
		servicePDF.exportPDFInvoice(response, factura.get());
	}
	
	@GetMapping("/facturasCliente/{id}")
	public List<Factura> facturasCliente(@PathVariable(value = "id") String cedula) throws IOException {
		List<Factura> facturas = servicioFacturas.findAll();
		List<Factura> facturasCliente = new ArrayList<Factura>();
		for (Factura factura : facturas) {
			if(factura.getCedula_cliente().equalsIgnoreCase(cedula)) {
				facturasCliente.add(factura);
			}
		}
		System.out.println("FACTURAS CLIENTE " + facturasCliente.size());
		return facturasCliente;
	}

	// Crear una factura
	// http://localhost:8080/facturas/registrarFactura
	@PostMapping("/registrarFactura")
	public String registrarFactura(HttpServletResponse response,
			@RequestBody FacturaDTO facturaDTO) throws IOException {
		Date fecha = new Date();
		int mes = fecha.getMonth() + 1;
		int dia = fecha.getDate();
		int anio = 2022;
		String fechaFinal = dia + "-" + mes + "-" + anio;
		Factura factura = new Factura();
		factura.setNombre_cliente(facturaDTO.getNombre_cliente());
		factura.setCedula_cliente(facturaDTO.getCedula_cliente());
		factura.setCantidad_producto(facturaDTO.getCantidad_producto());
		factura.setNombre_producto(facturaDTO.getNombre_producto());
		factura.setTelefono_cliente(facturaDTO.getTelefono_cliente());
		factura.setTotal_a_pagar(facturaDTO.getTotal_a_pagar());
		factura.setId_productos(null);
		factura.setFecha_factura(fechaFinal);
		List<Producto> prods = (List<Producto>) servicioProducto.findAll();
		System.out.println("PRODUCTOS " + facturaDTO.getProductos());
		List<Producto> productosAFacturar = new ArrayList<>();
			for (Producto producto : prods) {
				if(facturaDTO.getProductos().equalsIgnoreCase(producto.getNombre_producto())) {
					productosAFacturar.add(producto);
					
				}
			}
		
		System.out.println("PRODUCTOS A FACTURAR " + productosAFacturar);
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey="Content-Disposition";
		String headerValue = "attachment; filename=pdf_"+currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		servicioFacturas.save(factura);
		servicePDF.export(response, productosAFacturar);
		repoCarrito.deleteAll();
		return "/pdfs/pdf/generate";
	}

	// Editar factura
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarFactura(@RequestBody Factura facturaActualizada,
			@PathVariable(value = "id") int codigo) {

		Optional<Factura> oFactura = servicioFacturas.findById(codigo);
		if (!oFactura.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		// Este metodo sirve para modificar el objeto que queramos completo sin
		// necesidad de ir propiedad por propiedad
		// BeanUtils.copyProperties(facturaActualizada, oFactura.get());

		oFactura.get().setNombre_cliente(facturaActualizada.getNombre_cliente());
		oFactura.get().setNombre_producto(facturaActualizada.getNombre_producto());
		oFactura.get().setCantidad_producto(facturaActualizada.getCantidad_producto());
		oFactura.get().setTelefono_cliente(facturaActualizada.getTelefono_cliente());
		oFactura.get().setFecha_factura(facturaActualizada.getFecha_factura());
		oFactura.get().setTotal_a_pagar(facturaActualizada.getTotal_a_pagar());

		return ResponseEntity.status(HttpStatus.CREATED).body(servicioFacturas.save(oFactura.get()));
	}

	// Eliminar factura
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarFactura(@PathVariable(value = "id") int codigo) {

		if (!servicioFacturas.findById(codigo).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		servicioFacturas.deleteById(codigo);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/listaFacturas")
	public List<Factura> listarFacturas(HttpServletResponse response) throws IOException {
		List<Factura> facturas = servicioFacturas.findAll();
		if (facturas.isEmpty()) {
			return facturas;
		}
		
		return facturas;
		}
}
