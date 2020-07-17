package com.crud.model;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crud.model.DaoStuff;
import com.crud.model.Stuff;

public class StuffController extends HttpServlet {
	private static final long serialVersionUI = 1L;
	private DaoStuff stuffDao = DaoStuff.getInstance();
	private static final Logger LOGGER = Logger.getLogger(StuffController.class.getName());

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		try {
			switch (action) {
			case "/new":
				showNewForm(req, resp);
				break;

			case "/insert":
				insertStuff(req, resp);
				break;
			case "/delete":
				deleteStuff(req, resp);
				break;
			case "/edit":
				showEditForm(req, resp);
				break;
			case "/update":
				updateStuff(req, resp);
				break;
			default:
				listStuff(req, resp);
				break;
			}
		} catch (SQLException e) {
			// For simplicity just log the Exceptions;
			LOGGER.log(Level.SEVERE, "SQL Error", e);
		}
	}

	private void updateStuff(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ServletException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String location = req.getParameter("location");		
		Stuff stuff = new Stuff(id, name, description, quantity, location);
		stuffDao.update(stuff);
		resp.sendRedirect("list");

	}
	
	private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
		String id = req.getParameter("id");
		Optional<Stuff> existingStuff = stuffDao.find(id);
		RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/StuffFrom.jsp");
		existingStuff.ifPresent(s-> req.setAttribute("stuff", s));
		dispatcher.forward(req, resp);
	}
	
	private void deleteStuff(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
		int id = Integer.parseInt(req.getParameter("id"));
		
		Stuff stuff = new Stuff(id);
		stuffDao.delete(stuff);
		resp.sendRedirect("list");
	}

	private void insertStuff(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String location = req.getParameter("location");
		
		Stuff newStuff = new Stuff(name, description, quantity, location);
		stuffDao.save(newStuff);
		resp.sendRedirect("list");

	}
	
	private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/StuffFrom.jsp");
		dispatcher.forward(req, resp);
	}	
	
	private void listStuff(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/StuffList.jsp");
		
		List<Stuff> listStuff = stuffDao.findAll();
		req.setAttribute("listStuff", listStuff);
		
		dispatcher.forward(req,  resp);
	}



	



}