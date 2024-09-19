package org.example.service;

import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.example.dao.FoodDAO;
import org.example.model.Food;
import spark.Request;
import spark.Response;


public class FoodService {

    private FoodDAO foodDao = new FoodDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_NAME = 2;
    private final int FORM_ORDERBY_PRICE = 3;


    public FoodService() throws SQLException {
        makeForm();
    }


    public void makeForm() throws SQLException {
        makeForm(FORM_INSERT, new Food(), FORM_ORDERBY_NAME);
    }


    public void makeForm(int orderBy) throws SQLException {
        makeForm(FORM_INSERT, new Food(), orderBy);
    }


    public void makeForm(int tipo, Food aComida, int orderBy) throws SQLException {
        String nomeArquivo = "form.html";
        form = "";
        try{
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while(entrada.hasNext()){
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        }  catch (Exception e) { System.out.println(e.getMessage()); }

        String umaComida = "";
        if(tipo != FORM_INSERT) {
            umaComida += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/food/list/1\">Nova Comida</a></b></font></td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t</table>";
            umaComida += "\t<br>";
        }

        if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
            String action = "/food/";
            String name, nomeComida, buttonLabel;
            if (tipo == FORM_INSERT){
                action += "insert";
                name = "Inserir comida";
                nomeComida = "leite, pão, ...";
                buttonLabel = "Inserir";
            } else {
                action += "update/" + aComida.getId();
                name = "Atualizar a Comida (ID " + aComida.getId() + ")";
                nomeComida = aComida.getName();
                buttonLabel = "Atualizar";
            }
            // Dentro do método makeForm

            umaComida += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            umaComida += "\t<table class=\"form-table\" align=\"center\">";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td colspan=\"3\" class=\"form-header\">" + name + "</td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td colspan=\"3\">&nbsp;</td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td><label for=\"nome\">Nome: </label><input class=\"input--register\" type=\"text\" name=\"nome\" id=\"nome\" value=\"" + nomeComida + "\"></td>";
            umaComida += "\t\t\t<td><label for=\"preco\">Preço: </label><input class=\"input--register\" type=\"text\" name=\"preco\" id=\"preco\" value=\"" + aComida.getPrice() + "\"></td>";
            umaComida += "\t\t\t<td><label for=\"quantidade\">Quantidade: </label><input class=\"input--register\" type=\"text\" name=\"quantidade\" id=\"quantidade\" value=\"" + aComida.getQuantity() + "\"></td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td><label for=\"dataFabricacao\">Data de fabricação: </label><input class=\"input--register\" type=\"datetime-local\" name=\"dataFabricacao\" id=\"dataFabricacao\" value=\"" +
                    (aComida.getDateOfFabrication() != null ? aComida.getDateOfFabrication().toLocalDate().toString() + "T" + aComida.getDateOfFabrication().toLocalTime().toString() : "") +
                    "\"></td>";
            umaComida += "\t\t\t<td><label for=\"dataValidade\">Data de validade: </label><input class=\"input--register\" type=\"date\" name=\"dataValidade\" id=\"dataValidade\" value=\"" +
                    (aComida.getValidDate() != null ? aComida.getValidDate().toString() : "") +
                    "\"></td>";
            umaComida += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t</table>";
            umaComida += "\t</form>";

        } else if (tipo == FORM_DETAIL){
            umaComida += "\t<table width=\"100%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar aComida (ID " + aComida.getId() + ")</b></font></td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td>&nbsp;Nome: "+ aComida.getName() +"</td>";
            umaComida += "\t\t\t<td>Preco: "+ aComida.getPrice() +"</td>";
            umaComida += "\t\t\t<td>Quantidade: "+ aComida.getQuantity() +"</td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t\t<tr>";
            umaComida += "\t\t\t<td>&nbsp;Data de fabricação: "+ aComida.getDateOfFabrication().toString() + "</td>";
            umaComida += "\t\t\t<td>Data de validade: "+ aComida.getValidDate().toString() + "</td>";
            umaComida += "\t\t\t<td>&nbsp;</td>";
            umaComida += "\t\t</tr>";
            umaComida += "\t</table>";
        } else {
            System.out.println("ERRO! Tipo não identificado " + tipo);
        }
        form = form.replaceFirst("<UMA-COMIDA>", umaComida);

        String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
        list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de aComidas</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" +
                "\t<td><a href=\"/food/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/food/list/" + FORM_ORDERBY_NAME + "\"><b>Nome</b></a></td>\n" +
                "\t<td><a href=\"/food/list/" + FORM_ORDERBY_PRICE + "\"><b>Preço</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
                "</tr>\n";

        List<Food> aComidas;
        if (orderBy == FORM_ORDERBY_ID) {                 	aComidas = foodDao.getOrderByID();
        } else if (orderBy == FORM_ORDERBY_NAME) {		aComidas = foodDao.getOrderByName();
        } else if (orderBy == FORM_ORDERBY_PRICE) {			aComidas = foodDao.getOrderByPreco();
        } else {											aComidas = foodDao.get();
        }

        int i = 0;
        String bgcolor = "";
        for (Food p : aComidas) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" +
                    "\t<td>" + p.getId() + "</td>\n" +
                    "\t<td>" + p.getName() + "</td>\n" +
                    "\t<td>" + p.getPrice() + "</td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/food/" + p.getId() + "\"><img src=\"/image/details.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/food/update/" + p.getId() + "\"><img src=\"/image/up.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteComida('" + p.getId() + "', '" + p.getName() + "', '" + p.getPrice() + "');\"><img src=\"/image/trash.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "</tr>\n";
        }
        list += "</table>";
        form = form.replaceFirst("<LISTAR-COMIDA>", list);
    }


    public Object insert(Request request, Response response) throws SQLException {
        String name = request.queryParams("nome");
        float preco = Float.parseFloat(request.queryParams("preco"));
        int quantidade = Integer.parseInt(request.queryParams("quantidade"));
        LocalDateTime dataFabricacao = LocalDateTime.parse(request.queryParams("dataFabricacao"));
        LocalDate dataValidade = LocalDate.parse(request.queryParams("dataValidade"));

        String resp = "";

        Food comida = new Food(-1, name, preco, quantidade, dataFabricacao, dataValidade);

        if(foodDao.insert(comida) == true) {
            resp = "Comida (" + name + ") inserido!";
            response.status(201);
        } else {
            resp = "Comida (" + name + ") não inserido!";
            response.status(404);
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }


    public Object get(Request request, Response response) throws SQLException {
        int id = Integer.parseInt(request.params(":id"));
        Food aComida = (Food) foodDao.get(id);

        if (aComida != null) {
            response.status(200);
            makeForm(FORM_DETAIL, aComida, FORM_ORDERBY_NAME);
        } else {
            response.status(404);
            String resp = "Comida " + id + " não encontrado.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
        }

        return form;
    }


    public Object getToUpdate(Request request, Response response) throws SQLException {
        int id = Integer.parseInt(request.params(":id"));
        Food Comida = (Food) foodDao.get(id);

        if (Comida != null) {
            response.status(200);
            makeForm(FORM_UPDATE, Comida, FORM_ORDERBY_NAME);
        } else {
            response.status(404);
            String resp = "Comida " + id + " não encontrado.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
        }

        return form;
    }


    public Object getAll(Request request, Response response) throws SQLException {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return form;
    }

    public Object update(Request request, Response response) throws SQLException {
        int id = Integer.parseInt(request.params(":id"));
        Food Comida = foodDao.get(id);
        String resp = "";

        if (Comida != null) {
            String name = request.queryParams("nome");
            float preco = Float.parseFloat(request.queryParams("preco"));
            int quantidade = Integer.parseInt(request.queryParams("quantidade"));
            LocalDateTime dataFabricacao = LocalDateTime.parse(request.queryParams("dataFabricacao"));
            LocalDate dataValidade = LocalDate.parse(request.queryParams("dataValidade"));

            Comida.setName(name);
            Comida.setPrice(preco);
            Comida.setQuantity(quantidade);
            Comida.setDateOfFabrication(dataFabricacao);
            Comida.setValidDate(dataValidade);

            foodDao.update(Comida);
            response.status(200);
            resp = "Comida (ID " + Comida.getId() + ") atualizado!";
        } else {
            response.status(404);
            resp = "Comida (ID \" + Comida.getId() + \") não encontrado!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }


    public Object delete(Request request, Response response) throws SQLException {
        int id = Integer.parseInt(request.params(":id"));
        Food Comida = foodDao.get(id);
        String resp = "";

        if (Comida != null) {
            foodDao.delete(id);
            response.status(200);
            resp = "Comida (" + id + ") excluído!";
        } else {
            response.status(404);
            resp = "Comida (" + id + ") não encontrado!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }
}