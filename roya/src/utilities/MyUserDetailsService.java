
package utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bkeryah.entities.ArcUsers;
import com.bkeryah.entities.UserRoles;
import com.bkeryah.service.IDataAccessService;
import com.bkeryah.testssss.BeansManager;


public class MyUserDetailsService implements UserDetailsService {

//	@ManagedProperty(value="#{dataAccessService}")
	private IDataAccessService dataAccessService;

	@Override
	public UserDetails loadUserByUsername(final String username) 
               throws UsernameNotFoundException {

		Object dataAccessServ = BeansManager.getBean("dataAccessService");
		Object commonDao = BeansManager.getBean("commonDao");
		Object dataAccessDAO = BeansManager.getBean("dataAccessDAO");
		Object sessionFactory = BeansManager.getBean("SessionFactory");
		
		setDataAccessService((IDataAccessService) dataAccessServ);
		dataAccessService.injectDaos(commonDao, dataAccessDAO, sessionFactory);
		
		
		
		
		
		
		ArcUsers user = dataAccessService.loadUser(username.toUpperCase(), null); 
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());

		return buildUserForAuthentication(user, authorities);
	}

	// Converts user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(ArcUsers user, 
		List<GrantedAuthority> authorities) {
		boolean userIsActive = false;
		if( user.getIsActive()==1)
			userIsActive = true;
		return new User(user.getLoginName(), 
			user.getPassword(),userIsActive, 
                        true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRoles> userRoles) { 

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRoles userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}

}