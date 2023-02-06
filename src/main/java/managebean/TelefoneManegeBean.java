package managebean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoTelefone;
import dao.DaoUsuario;
import model.TelefoneUser;
import model.UsuarioPessoa;

@ManagedBean(name = "telefoneManegeBean")
@ViewScoped
public class TelefoneManegeBean {

	private UsuarioPessoa user = new UsuarioPessoa();
	private DaoUsuario<UsuarioPessoa> daoUser = new DaoUsuario<UsuarioPessoa>();
	private DaoTelefone<TelefoneUser> daoTelefone = new DaoTelefone<TelefoneUser>();
	private TelefoneUser telefone = new TelefoneUser();

	@PostConstruct
	public void init() {
		String codUser = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequestParameterMap()
				.get("codigouser");
		user = daoUser.pesquisar(Long.parseLong(codUser), UsuarioPessoa.class);
	}
	
	public String salvar() {
		telefone.setUsuarioPessoa(user);
		daoTelefone.salvar(telefone);
		telefone = new TelefoneUser();
		user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);
		FacesContext.getCurrentInstance()
		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ",
											"Telefone Salvo com Sucesso"));
		return "";
	}
	
	public String removeTelefone() throws Exception {
		
		daoTelefone.deletarPorId(telefone);
		user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);
		telefone = new TelefoneUser();
		FacesContext.getCurrentInstance()
		.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ",
											"Telefone Removido"));
		return""; 
	}
	
	public TelefoneUser getTelefone() {
		return telefone;
	}
	public void setTelefone(TelefoneUser telefone) {
		this.telefone = telefone;
	}

	public UsuarioPessoa getUser() {
		return user;
	}

	public void setUser(UsuarioPessoa user) {
		this.user = user;
	}
	public void setDaoTelefone(DaoTelefone<TelefoneUser> daoTelefone) {
		this.daoTelefone = daoTelefone;
	}
	public DaoTelefone<TelefoneUser> getDaoTelefone() {
		return daoTelefone;
	}

}
