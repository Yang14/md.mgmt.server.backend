package md.mgmt.service.impl;

import md.mgmt.base.md.ExactCode;
import md.mgmt.dao.RdbDao;
import md.mgmt.dao.RedisDao;
import md.mgmt.facade.req.PutMdAttrDto;
import md.mgmt.service.PutMdAttrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-14.
 */
@Component
public class PutMdAttrServiceImpl implements PutMdAttrService {
    private static Logger logger = LoggerFactory.getLogger(PutMdAttrServiceImpl.class);

    @Autowired
    private RdbDao rdbDao;

    @Autowired
    private RedisDao redisDao;

    @Override
    public boolean putMdAttr(PutMdAttrDto putMdAttrDto) {
        if (putMdAttrDto == null || putMdAttrDto.getExactCode() == null
                || putMdAttrDto.getMdAttr() == null) {
            return false;
        }
        ExactCode exactCode = putMdAttrDto.getExactCode();
        redisDao.setOrCreateHashBucket(exactCode.getDistrCode() + "", exactCode.getFileCode());
        return rdbDao.putMdAttr(exactCode.getFileCode(), putMdAttrDto.getMdAttr());
    }
}
