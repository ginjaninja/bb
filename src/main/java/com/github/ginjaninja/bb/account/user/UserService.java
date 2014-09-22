package com.github.ginjaninja.bb.account.user;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.message.ResultMessage;
import com.github.ginjaninja.bb.service.ServiceInterface;

@Service
@Transactional
public class UserService implements ServiceInterface<User>{
	final static Logger LOG = LoggerFactory.getLogger("UserService");
	
	@Autowired
	private UserDAO dao;
	
	

	/**
	 * Get user by id
	 * @param 	id 	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage get(Integer id) {
		User user = dao.get(id);
		if(user != null){
			return ResultMessage.success(user);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Save user 
	 * @param user 	{@link User}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage save(User user) {
		ResultMessage message = null;
		try{
			if(user.getId() == null){
				//set defaults
				user.fillFields();
				String result = user.checkRequired();
				//save if checkRequired message is empty
				if(result.length() == 0){
					user = dao.save(user);
					message = ResultMessage.success(user);
				}else{
					//return error with missing properties
					message = ResultMessage.missingProperties(result);
				}
			}else{
				//get stored user
				User storedUser = dao.get(user.getId());
				if(storedUser == null){
					message = ResultMessage.notFound();
				}else{
					//fill in null fields from stored object
					user.fillFields(dao);
					//update activity date time
					user.setActivityDtTm(new Date());
					dao.update(user);
					//refetch user with parent/child entities
					user = dao.get(user.getId());
					message = ResultMessage.success(user);
				}
			}
		}catch(PersistenceException pe){
			message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
		}catch(Exception e){
			message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
		}
		return message;
	}

	/**
	 * Delete user by id
	 * @param id	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage delete(Integer id) {
		ResultMessage message = null;
		User user = dao.get(id);
		if(user == null){
			message = ResultMessage.notFound();
		}else{
			try{
				dao.delete(user);
				message = ResultMessage.success(user);
			}catch(PersistenceException pe){
				message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
			}catch(Exception e){
				message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
			}
		}
		return message;
	}
	
	/**
	 * Get many with named query and params
	 * @param queryName {@link String}
	 * @param params	Map<String, Object>
	 * @return			{@link ResultMessage}
	 */
	@Override
	public ResultMessage getMany(String queryName, Map<String, Object> params) {
		Collection<User> users = dao.getMany(queryName, params);
		if(users != null){
			return ResultMessage.success(users);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Get many with named query
	 * @param queryName {@link String}
	 * @return			{@link ResultMessage}
	 */
	@Override
	public ResultMessage getMany(String queryName) {
		Collection<User> users = dao.getMany(queryName);
		if(users != null){
			return ResultMessage.success(users);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Deactivate user by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	@Override
	public ResultMessage deactivate(Integer id) {
		User user = dao.get(id);
		user.setActiveInd("N");
		return this.save(user);
	}

	/**
	 * Activate user by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	@Override
	public ResultMessage activate(Integer id) {
		User user = dao.get(id);
		user.setActiveInd("Y");
		return this.save(user);
	}

	
}
