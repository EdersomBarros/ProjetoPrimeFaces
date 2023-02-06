package dao;

import model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa>{
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		getEntityManager().getTransaction().begin();
		String sqlDeleteFone = "DELETE FROM telefoneuser WHERE usuariopessoa_id =" + pessoa.getId();
		getEntityManager().createNativeQuery(sqlDeleteFone).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
	}

}
