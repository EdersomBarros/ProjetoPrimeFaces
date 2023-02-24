package managebean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import dao.DaoEmail;
import dao.DaoUsuario;
import model.EmailUser;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManageBean")
@ViewScoped
public class UsuarioPessoaManageBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	private DaoUsuario<UsuarioPessoa> daoGeneric = new DaoUsuario<UsuarioPessoa>();
	private BarChartModel barChartModel = new BarChartModel();
	private EmailUser emailUser = new EmailUser();
	private DaoEmail<EmailUser> daoEmail = new  DaoEmail<EmailUser>();

	@PostConstruct
	public void init() {
		list = daoGeneric.listar(UsuarioPessoa.class);

		ChartSeries userSalario = new ChartSeries();/* Grupo de funcionários */

		for (UsuarioPessoa usuarioPessoa : list) {

			userSalario.set(usuarioPessoa.getNome(), usuarioPessoa.getSalario());

		}
		barChartModel.addSeries(userSalario);
		barChartModel.setTitle("Gráfico de Salários");
	}

	public String salvar() {

		daoGeneric.salvar(usuarioPessoa);
		list.add(usuarioPessoa);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com Sucesso"));

		return "";
	}

	public String novo() {
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}

	public String remover() {
		try {
			daoGeneric.removerUsuario(usuarioPessoa);
			list.remove(usuarioPessoa);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com Sucesso"));
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Informação: ", "Existem Telefone para o usuário!"));

			}
		}
		return "";
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {

			URL url = new URL("https://viacep.com.br/ws/" + usuarioPessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = br.readLine()) != null) {

				jsonCep.append(cep);
			}

			UsuarioPessoa userCepPessoa = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);

			usuarioPessoa.setCep(userCepPessoa.getCep());
			usuarioPessoa.setLogradouro(userCepPessoa.getLogradouro());
			usuarioPessoa.setBairro(userCepPessoa.getBairro());
			usuarioPessoa.setLocalidade(userCepPessoa.getLocalidade());
			usuarioPessoa.setUf(userCepPessoa.getUf());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	public void addEmail() {
		emailUser.setUsuarioPessoa(usuarioPessoa);
		emailUser = daoEmail.updateMerge(emailUser);
		usuarioPessoa.getEmails().add(emailUser);
		emailUser = new EmailUser();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Sucesso", "E-mail salvo com sucesso!"));
				
	}

	public void removerEmail() throws Exception {

		String codigoEmail = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequestParameterMap()
				.get("codigoemail");
		EmailUser remover = new EmailUser();
		remover.setId(Long.parseLong(codigoEmail));
		daoEmail.deletarPorId(remover);
		usuarioPessoa.getEmails().remove(remover);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "E-mail Removido com sucesso!"));
	}

	public DaoEmail<EmailUser> getDaoEmail() {
		return daoEmail;
	}

	public void setDaoEmail(DaoEmail<EmailUser> daoEmail) {
		this.daoEmail = daoEmail;
	}

	public void setEmailUser(EmailUser emailUser) {
		this.emailUser = emailUser;
	}

	public EmailUser getEmailUser() {
		return emailUser;
	}

	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	public List<UsuarioPessoa> getList() {
		return list;
	}

	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

}
