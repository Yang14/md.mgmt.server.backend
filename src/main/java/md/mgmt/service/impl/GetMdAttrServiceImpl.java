package md.mgmt.service.impl;

import md.mgmt.base.md.MdAttr;
import md.mgmt.dao.RdbDao;
import md.mgmt.dao.RedisDao;
import md.mgmt.service.GetMdAttrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-14.
 */
@Component
public class GetMdAttrServiceImpl implements GetMdAttrService {

    private static Logger logger = LoggerFactory.getLogger(GetMdAttrServiceImpl.class);

    @Autowired
    private RdbDao rdbDao;

    @Autowired
    private RedisDao redisDao;

    @Override
    public MdAttr getFileMdAttr(String fileCode) {
        return rdbDao.getFileMdAttr(fileCode);
    }

    @Override
    public List<MdAttr> getDirMdAttrList(String distrCode) {
        List<String> fileCodes = redisDao.getHashBucket(distrCode);
        return rdbDao.getDirMdAttrList(fileCodes);
    }
}
