package com.tonyzampogna.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.tonyzampogna.config.MongoConfiguration;
import com.tonyzampogna.model.User;

@RequestMapping("/")
@Controller
public class UserController {
 
	@RequestMapping(value={ "", "/", "/Users" }, method = RequestMethod.GET)
	public String index(ModelMap model) {
		return "index";
	}
 
	@RequestMapping(value="/services/user", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestBody User user, ModelMap model) {
	    MongoOperations mongoOperations = getMongoOperations();
		
		// Save
		mongoOperations.save(user);
		
		return "";
	}

	@RequestMapping(value="/services/users", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUsers(WebRequest webRequest, ModelMap model) {
		MongoOperations mongoOperations = getMongoOperations();
 
		Query searchUserQuery = null;
		List<User> users = new ArrayList<User>();
		
		String searchString = webRequest.getParameter("q");
		if (searchString == null || searchString.equals("")) {
			users.addAll(mongoOperations.findAll(User.class));
		}
		else {
			// Query IDs
			searchUserQuery = new Query(Criteria.where("id").is(searchString));
			users.addAll(mongoOperations.find(searchUserQuery, User.class));
			
			// Query Usernames
			searchUserQuery = new Query(Criteria.where("username").regex(searchString));
			users.addAll(mongoOperations.find(searchUserQuery, User.class));
			
			// Query Passwords
			searchUserQuery = new Query(Criteria.where("password").regex(searchString));
			users.addAll(mongoOperations.find(searchUserQuery, User.class));
			
			// Query Emails
			searchUserQuery = new Query(Criteria.where("email").regex(searchString));
			users.addAll(mongoOperations.find(searchUserQuery, User.class));
			
			// Query Abouts
			searchUserQuery = new Query(Criteria.where("about").regex(searchString));
			users.addAll(mongoOperations.find(searchUserQuery, User.class));
		}
		
		return users;
	}

	@RequestMapping(value="/services/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getUserById(@PathVariable String id, ModelMap model) {
	    MongoOperations mongoOperations = getMongoOperations();
	    
		Query searchUserQuery = new Query(Criteria.where("id").is(id));
		User user = mongoOperations.findOne(searchUserQuery, User.class);
		
		return user;
	}

	@RequestMapping(value="/services/user", method = RequestMethod.PUT)
	@ResponseBody
	public String updateUser(@RequestBody User user, ModelMap model) {
	    MongoOperations mongoOperations = getMongoOperations();
	    
		Query searchUserQuery = new Query(Criteria.where("id").is(user.getId()));
		
		Update update = new Update();
		update.set("username", user.getUsername());
		update.set("password", user.getPassword());
		update.set("email", user.getEmail());
		update.set("about", user.getAbout());
		
		mongoOperations.updateFirst(searchUserQuery, update, User.class);
		
		return "";
	}

	@RequestMapping(value="/services/user", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteUser(@RequestBody User user, ModelMap model) {
	    MongoOperations mongoOperations = getMongoOperations();
	    
		Query searchUserQuery = new Query(Criteria.where("id").is(user.getId()));
		mongoOperations.remove(searchUserQuery, User.class);
		
		return "";
	}
	
	
	/**
	 * Return a handle to Mongo functions.
	 * 
	 * @return MongoOperations
	 */
	private MongoOperations getMongoOperations() {
		ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfiguration.class);
	    MongoOperations mongoOperations = (MongoOperations) context.getBean("adminMongoTemplate");
	    return mongoOperations;
	}

}