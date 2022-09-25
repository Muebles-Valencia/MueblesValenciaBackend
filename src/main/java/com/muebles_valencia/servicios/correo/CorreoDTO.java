package com.muebles_valencia.servicios.correo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;

public class CorreoDTO {
	
	private List<String> destinatarios;
    private String titulo;
    private String contenido;
    private List<File> adjuntos;
    private Document adjuntos2;
    private String URL;
    private String fileName;
    
    public CorreoDTO() {
        destinatarios = new ArrayList<>();
        adjuntos = new ArrayList<>();
        adjuntos2 = new Document();
    }
    
    
    public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public List<String> getDestinatarios() {
        return destinatarios;
    }
    
    public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		this.URL = uRL;
	}

	public void setDestinatarios(List<String> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public List<File> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<File> adjuntos) {
        this.adjuntos = adjuntos;
    }


	public Document getAdjuntos2() {
		return adjuntos2;
	}


	public void setAdjuntos2(Document adjuntos2) {
		this.adjuntos2 = adjuntos2;
	}
    
    
}
