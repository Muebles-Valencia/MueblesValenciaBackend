package com.muebles_valencia.servicios.correo;

import java.awt.Image;
import java.awt.image.ImageProducer;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.muebles_valencia.entidades.producto.Producto;

@Service
public class EnviadorDeCorreos {

	/**
	 * public static void sendEmail(String to, String subject, String body) { try {
	 * sendEmail(to, subject, body, null); } catch (Exception e) {
	 * System.out.println("ERROR " + e); } }
	 **/
	public void sendEmail(String to, String nombreCliente, Producto prodReservar) {

		try {
			CorreoDTO dto = new CorreoDTO();
			dto.getDestinatarios().add(to);
			dto.setContenido("Hola " + nombreCliente + " se ha reservado el producto exitosamente "
					+ prodReservar.getDescripcion_producto());
			dto.setTitulo("Producto reservado con exito");
			if(prodReservar.getFoto_producto() != null || prodReservar.getFoto_producto() != "") {
				dto.setURL(prodReservar.getFoto_producto());
				File archivo = new File("C:\\Users\\Public\\Pictures\\" + dto.getFileName());
				dto.getAdjuntos().add(archivo);
			}
			EnviarCorreo enviarCorreo = new EnviarCorreo(dto);
			enviarCorreo.start();
			System.out.println("CORREO ENVIADO ");
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}
	}
	
	public void sendEmailAdmin(String mensaje , Producto prodReservar) {

		try {
			CorreoDTO dto = new CorreoDTO();
			dto.getDestinatarios().add("valenciamuebles49@gmail.com");
			dto.setContenido(mensaje);
			dto.setTitulo("Producto reservado con exito");
			if(prodReservar.getFoto_producto() != null || prodReservar.getFoto_producto() != "") {
				dto.setURL(prodReservar.getFoto_producto());
				File archivo = new File("C:\\Users\\Public\\Pictures\\" + dto.getFileName());
				dto.getAdjuntos().add(archivo);
			}
			EnviarCorreo enviarCorreo = new EnviarCorreo(dto);
			enviarCorreo.start();
			System.out.println("CORREO ENVIADO ");
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}
	}
	
	public String sendEmailResetPassword(String correo ) {

		try {
			CorreoDTO dto = new CorreoDTO();
			Double aleatorio = 0.0;
			aleatorio = (Math.random());
			String r = "";
			String s = r.valueOf(aleatorio);
			int m = s.length() - 6;
			String last6numbers = s.substring(m , s.length());
			System.out.println("NUMEROS ALEATORIOS " + aleatorio + " S " + s + " PARSEADO " +  last6numbers);
			
			dto.getDestinatarios().add(correo);
			dto.setContenido("Este es el codigo de recuperacion para la cuenta " + last6numbers);
			dto.setTitulo("Correo de recuperacion de contraseña ");
			
			EnviarCorreo enviarCorreo = new EnviarCorreo(dto);
			enviarCorreo.start();
			System.out.println("CORREO ENVIADO ");
			return last6numbers;
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
			return "Error";
		}
	}
	
}
