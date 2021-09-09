/*
 * AbstractController.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.security.CredentialProvider;
import br.ueg.modelo.application.model.Usuario;
import br.ueg.modelo.application.security.CredentialImpl;
import br.ueg.modelo.application.service.UsuarioService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Abstract Controller.
 * 
 * @author UEG
 */
public abstract class AbstractController {

	@Value("${app.report.output.path}")
	private String REPORT_OUTPUT_PATH;


	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Exporta o {@link JasperPrint} no formato PDF conforme os parâmetros
	 * informados.
	 * 
	 * @param print -
	 * @param name -
	 * @return -
	 * @throws JRException -
	 * @throws IOException -
	 */
	protected ResponseEntity<InputStreamResource> toPDF(final JasperPrint print, final String name)
			throws JRException, IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			JasperExportManager.exportReportToPdfStream(print, out);

			try (ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray())) {
				return ResponseEntity.ok().header("Content-Disposition", "inline; filename=" + name.trim())
						.contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(input));
			}
		}
	}

	/**
	 * @return
	 */
	protected CredentialImpl getCredential() {
		return CredentialProvider.newInstance().getCurrentInstance(CredentialImpl.class);
	}

	/**
	 * @return the idUsuarioLogado
	 */
	protected Long getIdUsuarioLogado() {
		CredentialImpl credential = getCredential();
		return credential != null ? credential.getIdUsuario() : null;
	}


	/**
	 * @return Retorna a instância do {@link Usuario} logado.
	 */
	protected Usuario getUsuarioLogado() {
		Usuario usuario = null;
		Long idUsuario = getIdUsuarioLogado();

		if (idUsuario != null) {
			usuario = usuarioService.getById(idUsuario);
		}
		return usuario;
	}

}
