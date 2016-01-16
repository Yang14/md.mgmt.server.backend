package md.mgmt.service.impl;

import md.mgmt.base.md.MdAttr;
import md.mgmt.dao.RdbDao;
import md.mgmt.facade.req.RenameMdAttrDto;
import md.mgmt.service.RenameMdAttrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mr-yang on 16-1-16.
 */
@Component
public class RenameMdAttrServiceImpl implements RenameMdAttrService {
    private static Logger logger = LoggerFactory.getLogger(RenameMdAttrServiceImpl.class);

    @Autowired
    private RdbDao rdbDao;

    @Override
    public boolean renameMdAttr(RenameMdAttrDto renameMdAttrDto) {
        if (renameMdAttrDto == null || renameMdAttrDto.getFileCode() == null
                || renameMdAttrDto.getNewName() == null) {
            return false;
        }
        String fileCode = renameMdAttrDto.getFileCode();
        String newName = renameMdAttrDto.getNewName();
        MdAttr mdAttr = rdbDao.getFileMdAttr(fileCode);
        if (mdAttr == null){
            logger.error(String.format("renameMdAttr: no such file, fileCode is %s",fileCode));
            return false;
        }
        mdAttr.setName(newName);
        return rdbDao.putMdAttr(fileCode, mdAttr);
    }
}
