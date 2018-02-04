package com.lbs.nettyserver.dao.vomit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lbs.nettyserver.dao.common.BaseDao;

@Transactional
@Repository("vomitDAO")
public class VomitDAO {

	@Autowired 
    private BaseDao baseDao;
	
	
}
