package projetohibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {
	
	@Test
	public void testeHibernateUtil() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = new UsuarioPessoa();
		
		pessoa.setNome("Eder Barros 9");
		pessoa.setSobrenome("Rodrigues");
		pessoa.setIdade(78);
		pessoa.setLogin("testegtvtvgvrfvrfvrv");
		pessoa.setSenha("123fvrfvvvvvvvv");
		pessoa.setEmail("edersrvfvfvfvfvvrombarros@gmail.com");
		
		daoGeneric.salvar(pessoa);
		
		
		}
	@Test
	public void testBuscar() {
		
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L,UsuarioPessoa.class);
		
		System.out.println(pessoa);
		
	}
	@Test
	public void testUpdate() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L,UsuarioPessoa.class);
		
		pessoa.setId(2L);
		pessoa.setIdade(88);
		
		
		pessoa = daoGeneric.updateMerge(pessoa);
		
		System.out.println(pessoa);
		
	}
	@Test
	public void testDelete() throws Exception {
		
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = daoGeneric.pesquisar(1L,UsuarioPessoa.class);
		
		daoGeneric.deletarPorId(pessoa);
		
	}
	
	@Test
	public void testConsultar() {
		
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> list = daoGeneric.listar(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("---------------------------------------");
		}
	}
	
	@Test
	public void testeQueryList() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		@SuppressWarnings("unchecked")
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
		@Test
		public void testeQueryListMaxResult() {

			DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

			List<UsuarioPessoa> list = daoGeneric.getEntityManager()
							.createQuery(" from UsuarioPessoa order by nome ")
							.setMaxResults(4)
							.getResultList();
			
			for (UsuarioPessoa usuarioPessoa : list) {
				System.out.println(usuarioPessoa);
			}
					
	}
		
	@Test
	public void testeQueryListParameter() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager()
				.createQuery(" from UsuarioPessoa where nome = : nome")
				.setParameter("nome","Eder atualizada")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}
	@Test
	public void testeQuerySomaIdade() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		Long somaIdade = (Long) daoGeneric.getEntityManager()
				.createQuery("select sum(u.idade) from UsuarioPessoa u")
				.getSingleResult();

		
			System.out.println("Soma de todas as idades é ---> "+somaIdade);
		}
	
		@Test
		public void testeNamedQuery1() {
			
			DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
			
			List<UsuarioPessoa> list = daoGeneric.getEntityManager()
					.createNamedQuery("UsuarioPessoa.todos")
					.getResultList();
			
			for (UsuarioPessoa usuarioPessoa : list) {
				System.out.println(usuarioPessoa);
			}
			

		}
		

		@Test
		public void testeNamedQuery2() {
			
			DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
			
			List<UsuarioPessoa> list = daoGeneric.getEntityManager()
					.createNamedQuery("UsuarioPessoa.buscaPorNome")
					.setParameter("nome", "Eder Barros 9")
					.getResultList();
			
			for (UsuarioPessoa usuarioPessoa : list) {
				System.out.println(usuarioPessoa);
			}
			

		}
	
		@Test
		public void testeGravaTelefone() {
			
			DaoGeneric daoGeneric = new DaoGeneric();
			
			UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(7L, UsuarioPessoa.class);
			
			TelefoneUser telefoneUser = new TelefoneUser();
			telefoneUser.setTipo("Casa");
			telefoneUser.setNumero("6799585555699");
			telefoneUser.setUsuarioPessoa(pessoa);
			
			daoGeneric.salvar(telefoneUser);
			
			
		}
		@Test
		public void testeConsultaTelefones() {
			
			DaoGeneric daoGeneric = new DaoGeneric();
			
			UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(7L, UsuarioPessoa.class);
			
			for (TelefoneUser fone: pessoa.getTelefoneUsers()) {
				
				System.out.println(fone.getTipo());
				System.out.println(fone.getNumero());
				System.out.println(fone.getUsuarioPessoa().getNome());
				System.out.println("------------------------------------");
				
			}
			

		}
	
	
	
	}



