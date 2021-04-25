package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Admin;

//For REST Service

@Path("/admin")
public class AdminService {
	
	Admin itemObj = new Admin();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
		return itemObj.readItems();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(
			@FormParam("password") String password,
	@FormParam("userName") String userName,
	@FormParam("category") String category,
	@FormParam("describe") String describe)
	{
	String output = itemObj.insertItem(password, userName, category, describe);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateItem(String itemData)
	{
	//Convert the input string to a JSON object
	JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	String userID = itemObject.get("userID").getAsString();
	String password = itemObject.get("password").getAsString();
	String userName = itemObject.get("userName").getAsString();
	String category = itemObject.get("category").getAsString();
	String describe = itemObject.get("describe").getAsString();
	String output = itemObj.updateItem(userID, password, userName, category, describe);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
	//Read the value from the element <itemID>
	String userID = doc.select("userID").text();
	String output = itemObj.deleteItem(userID);
	return output;
	}
	
	
}
