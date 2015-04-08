package br.com.alura.gerenciador.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/executa")
public class Controller extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String tarefa = req.getParameter("tarefa");
		if (tarefa == null) {
			throw new IllegalArgumentException("Você esquece de passar a tarefa");
		}
		
		try {
			String nomeDaClasse = "br.com.alura.gerenciador.web." + tarefa;
			// pegar o nome da classe em tempo de execução (criação dinâmica de objetos)
			Class<?> type = Class.forName(nomeDaClasse);
			Tarefa instancia = (Tarefa) type.newInstance();
			String pagina = instancia.executa(req, resp);
			req.getRequestDispatcher(pagina).forward(req, resp);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}
}
