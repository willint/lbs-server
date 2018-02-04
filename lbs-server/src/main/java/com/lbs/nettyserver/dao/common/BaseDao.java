package com.lbs.nettyserver.dao.common;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Transactional
@Repository("baseDao")
public class BaseDao {
	
	@Autowired
	private SessionFactory sessionFactory;
/**
 * 执行自定义sql语句
 * @param sql，自定义sql
 * @param params,sql中的参数值
 * @param entityClass，返回的实体类
 * @return
 * @throws Exception
 */
	@SuppressWarnings("unchecked")
	public <T>List<T> selectNativeSqlList(String sql, Map<String, Object> params, Class<T> entityClass) throws Exception {
		SQLQuery sqlQuery=this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		if(params != null){
			for(Map.Entry<String, Object> entry: params.entrySet()){
				sqlQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		sqlQuery.setResultTransformer(Transformers.aliasToBean(entityClass));
		return sqlQuery.list();
	}
/**
 * 执行自定义sql语句
 * @param sql，自定义sql
 * @param entityClass，返回的实体类
 * @return
 * @throws Exception
 */
	public <T>List<T> selectNativeSqlList(String sql, Class<T> entityClass) throws Exception {
		return selectNativeSqlList(sql, null, entityClass);
	}
	
	/**
	 * 执行自定义sql语句，返回 columName指定列名对应的值
	 * @param sql
	 * @param params
	 * @param columName
	 * @return
	 * @throws Exception
	 */
	public Object selectNativeSqlUniqueResult(String sql, Map<String, Object> params, String columName) throws Exception {
		
		SQLQuery sqlQuery=this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		if(params != null){
			for(Map.Entry<String, Object> entry: params.entrySet()){
				sqlQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return sqlQuery.addScalar(columName).uniqueResult();
	}
}
