package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.UsuarioPessoa;
import projetohibernate.HibernateUtil;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();

	public void salvar(E entidade) {

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();

	}

	public E pesquisar(long id, Class<E> entidade) {

		E e = (E) entityManager.find(entidade, id);

		return e;

	}

	public E updateMerge(E entidade) { // salva ou atualiza

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeSalva = entityManager.merge(entidade);
		transaction.commit();

		return entidadeSalva;

	}

	public void deletarPorId(E entidade) {

		Object id = HibernateUtil.getPrimaryKey(entidade);
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		entityManager
				.createNativeQuery(
						"delete from " + entidade.getClass().getSimpleName().toLowerCase() + " where id = " + id)
				.executeUpdate(); // faz delete

		transaction.commit(); // grava alteração no banco

	}

	public List<E> listar(Class<E> entidade) {

		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();

		transaction.commit();

		return lista;

	}
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
